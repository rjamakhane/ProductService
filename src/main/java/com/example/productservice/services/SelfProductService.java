package com.example.productservice.services;

import com.example.productservice.dtos.UserDto;
import com.example.productservice.exceptions.InvalidProductIdException;
import com.example.productservice.modals.Category;
import com.example.productservice.modals.Product;
import com.example.productservice.repositories.CategoryRepository;
import com.example.productservice.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
@Primary
public class SelfProductService implements ProductService{
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    private RestTemplate restTemplate;

    SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository, RestTemplate restTemplate){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.restTemplate = restTemplate;
    }
    @Override
    public Product getProductById(Long id) throws InvalidProductIdException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new InvalidProductIdException("Invalid Product Id: " + id);
        }
        // if product is not found, then return null or you can also
        UserDto userDto = restTemplate.getForObject("http://UserService/users/" + id, UserDto.class);
        // throw product not found exception
        return optionalProduct.orElse(null);

    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize, String sortDirection, String sortBy) {
        //fetch all the products from db
        if(sortDirection.equals("asc")){
            return productRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending()));
        } else {
            return productRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending()));
        }
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
