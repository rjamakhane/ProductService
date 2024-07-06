package com.example.productservice.repositories;

import com.example.productservice.modals.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    Category save(Category category);
}
