package com.example.productservice.controllers;

import com.example.productservice.commons.AuthenticationCommons;
import com.example.productservice.dtos.FakeStoreProductDTO;
import com.example.productservice.dtos.UserDto;
import com.example.productservice.exceptions.InvalidProductIdException;
import com.example.productservice.modals.Category;
import com.example.productservice.modals.Product;
import com.example.productservice.modals.ProductDocument;
import com.example.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private AuthenticationCommons authenticationCommons;

    ProductController(@Qualifier("selfProductService") ProductService productService, AuthenticationCommons authenticationCommons) {
        this.productService = productService;
        this.authenticationCommons = authenticationCommons;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) throws InvalidProductIdException {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/all/{token}")
    public ResponseEntity<Page<Product>> getAllProducts(@PathVariable("token") String token, @RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("sortDirection") String sortDirection, @RequestParam("sortBy") String sortBy){
//        localhost:2020/products/?pageNumber=0&pageSize=10
        UserDto userDto = authenticationCommons.validateToken(token);
        if(userDto == null){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        Page<Product> products = productService.getAllProducts(pageNumber,pageSize, sortDirection, sortBy);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/search/{searchString}")
    public ResponseEntity<Page<ProductDocument>> getAllProducts(@PathVariable("searchString") String searchString, @RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize, @RequestParam("sortDirection") String sortDirection){
        Page<ProductDocument> products = productService.searchByProductName(searchString, pageNumber,pageSize, sortDirection);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) throws InvalidProductIdException {
        return productService.updateProduct(id, product);
    }

    @PutMapping("/{id}")
    public Product replaceProduct(@PathVariable("id") Long id, @RequestBody Product product) throws InvalidProductIdException {
        return productService.replaceProduct(id, product);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

    @DeleteMapping("/{id}")
    public Product deleteProduct(@PathVariable("id") Long id) throws InvalidProductIdException {
        return productService.deleteProduct(id);
    }
}
