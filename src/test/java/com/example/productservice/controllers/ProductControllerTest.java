package com.example.productservice.controllers;

import com.example.productservice.exceptions.InvalidProductIdException;
import com.example.productservice.modals.Category;
import com.example.productservice.modals.Product;
import com.example.productservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private  ProductController productController;

    @MockBean
    private ProductService productService;
    @Test
    void getProductByIdValidCase() throws InvalidProductIdException {
        Product product = new Product();
        product.setId(10L);
        product.setTitle("Test");
        product.setPrice(1000.0);
        when(productService.getProductById(10L)).thenReturn(product);

        ResponseEntity<Product> expectedProduct = productController.getProduct(10L);
        assertEquals(product, expectedProduct.getBody());
        assertEquals(HttpStatus.OK, expectedProduct.getStatusCode());
    }

    @Test
    void getProductByIdInValidCase() {

    }

    @Test
    void getAllProducts() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void replaceProduct() {
    }

    @Test
    void createProduct() {
    }
}