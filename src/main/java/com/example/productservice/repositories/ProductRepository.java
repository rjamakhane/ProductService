package com.example.productservice.repositories;

import com.example.productservice.modals.Product;
import com.example.productservice.repositories.projections.ProductByIdAndTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    Optional<Product> findByTitle(String title);
    void  deleteById(Long id);
    void deleteByTitle(String title);
    Product save(Product product);

    @Query("SELECT p.id as id, p.title as title FROM Product p")
    List<ProductByIdAndTitle> someRandomQuery();

}

