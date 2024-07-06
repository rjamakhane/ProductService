package com.example.productservice.services;

import com.example.productservice.exceptions.InvalidProductIdException;
import com.example.productservice.modals.Category;
import com.example.productservice.modals.Product;
import com.example.productservice.repositories.CategoryRepository;
import com.example.productservice.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService{
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Product getProductById(Long id) throws InvalidProductIdException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new InvalidProductIdException("Invalid Product Id: " + id);
        }
        // throw product not found exception
        return optionalProduct.orElse(null);

    }

    @Override
    public List<Product> getAllProducts() {
        //fetch all the products from db
        return  productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        Category category = product.getCategory();
        if(category.getId() == null){
            category = categoryRepository.save(category);
            product.setCategory(category);
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) throws InvalidProductIdException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new InvalidProductIdException("Invalid Product Id: "+ id);
        }
        Product currentProduct = optionalProduct.get();
        if(product == null) throw new RuntimeException("Invalid input");
        if(product.getTitle() != null){
            currentProduct.setTitle(product.getTitle());
        }
        if(product.getDescription() != null){
            currentProduct.setDescription(product.getDescription());
        }
        if(product.getPrice() != 0.0){
            currentProduct.setPrice(product.getPrice());
        }
        if(product.getCategory() != null){
            Category category = product.getCategory();
            if(category.getId() == null){
                category = categoryRepository.save(category);
            }
            currentProduct.setCategory(category);
        }
        if(product.getImage() != null){
            currentProduct.setImage(product.getImage());
        }
        return productRepository.save(currentProduct);
    }

    @Override
    public Product replaceProduct(Long id, Product product) throws InvalidProductIdException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new InvalidProductIdException("Product with id " + id + " not found");
        }
        Product existingProduct = optionalProduct.get();

        // Assuming all fields of the product are to be replaced.
        existingProduct.setTitle(product.getTitle());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setImage(product.getImage());

        // Save and return the updated product
        return productRepository.save(existingProduct);
    }
    @Override
    public Product deleteProduct(Long id) throws InvalidProductIdException {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new InvalidProductIdException("Product with id " + id + " not found");
        }
        Product product = optionalProduct.get();
        productRepository.delete(product);
        return product;
    }
}
