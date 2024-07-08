package com.example.productservice.services;

import com.example.productservice.exceptions.InvalidProductIdException;
import com.example.productservice.modals.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    Product getProductById(Long id) throws InvalidProductIdException;
    Page<Product> getAllProducts(int pageNumber, int pageSize, String sortDirection, String sortBy);
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product) throws InvalidProductIdException;
    Product replaceProduct(Long id, Product product) throws InvalidProductIdException;
    Product deleteProduct(Long id) throws InvalidProductIdException;

}
