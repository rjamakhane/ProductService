package com.example.productservice.services;

import com.example.productservice.exceptions.InvalidProductIdException;
import com.example.productservice.modals.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    Product getProductById(Long id) throws InvalidProductIdException;
    List<Product> getAllProducts();
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product) throws InvalidProductIdException;
    Product replaceProduct(Long id, Product product) throws InvalidProductIdException;
    Product deleteProduct(Long id) throws InvalidProductIdException;

}
