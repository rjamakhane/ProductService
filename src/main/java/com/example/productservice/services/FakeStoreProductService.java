package com.example.productservice.services;

import com.example.productservice.dtos.FakeStoreProductDTO;
import com.example.productservice.exceptions.InvalidProductIdException;
import com.example.productservice.modals.Category;
import com.example.productservice.modals.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
@Primary
public class FakeStoreProductService implements ProductService{
    private RestTemplate restTemplate;
    private RedisTemplate redisTemplate;

    FakeStoreProductService(RestTemplate restTemplate, RedisTemplate redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }
    @Override
    public Product getProductById(Long id) throws InvalidProductIdException {
        // first check in the cache if product exist
        Product product = (Product) redisTemplate.opsForHash().get("PRODUCT", "PRODUCT_" + id);
        if(product != null){
            // if found in cache return it
            return product;
        }
        FakeStoreProductDTO fakeStoreProductDTO = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeStoreProductDTO.class);
        if (fakeStoreProductDTO == null) {
            throw new InvalidProductIdException("Invalid Product Id");
        }
        product = convertFakeStoreProductDTOtoProduct(fakeStoreProductDTO);
        redisTemplate.opsForHash().put("PRODUCT", "PRODUCT_" + id, product);
        return product;
    }

    public Product convertFakeStoreProductDTOtoProduct(FakeStoreProductDTO fakeStoreProductDTO) {
        Product product = new Product();
        product.setId(fakeStoreProductDTO.getId());
        product.setTitle(fakeStoreProductDTO.getTitle());
        product.setPrice(fakeStoreProductDTO.getPrice());

        Category category = new Category();
        category.setTitle(fakeStoreProductDTO.getCategory());

        product.setCategory(category);
        product.setDescription(fakeStoreProductDTO.getDescription());
        product.setImage(fakeStoreProductDTO.getImage());
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDTO[] fakeStoreProductDTO = restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreProductDTO[].class);
        if (fakeStoreProductDTO != null) {
            List<Product> products = new ArrayList<>();
            for (FakeStoreProductDTO productDTO : fakeStoreProductDTO) {
                products.add(convertFakeStoreProductDTOtoProduct(productDTO));
            }
            return products;
        }
        return new ArrayList<Product>();
    }

    public FakeStoreProductDTO convertProductToFakeStoreProductDTO(Product product) {
        FakeStoreProductDTO fakeStoreProductDTO = new FakeStoreProductDTO();
//        fakeStoreProductDTO.setId(product.getId());
        fakeStoreProductDTO.setTitle(product.getTitle());
        fakeStoreProductDTO.setPrice(product.getPrice());
        fakeStoreProductDTO.setDescription(product.getDescription());
        fakeStoreProductDTO.setImage(product.getImage());
        fakeStoreProductDTO.setCategory(product.getCategory().getTitle());
        return fakeStoreProductDTO;
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDTO fakeStoreProductDTORequest = convertProductToFakeStoreProductDTO(product);
        FakeStoreProductDTO fakeStoreProductDTO = restTemplate.postForObject("https://fakestoreapi.com/products", fakeStoreProductDTORequest, FakeStoreProductDTO.class);
        return fakeStoreProductDTO != null ? convertFakeStoreProductDTOtoProduct(fakeStoreProductDTO) : null;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        FakeStoreProductDTO fakeStoreProductDTORequest = convertProductToFakeStoreProductDTO(product);
        FakeStoreProductDTO fakeStoreProductDTO = restTemplate.patchForObject("https://fakestoreapi.com/products/" + id, fakeStoreProductDTORequest, FakeStoreProductDTO.class);
        return fakeStoreProductDTO != null ? convertFakeStoreProductDTOtoProduct(fakeStoreProductDTO) : null;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(product, FakeStoreProductDTO.class);
        HttpMessageConverterExtractor<FakeStoreProductDTO> responseExtractor = new HttpMessageConverterExtractor<>(FakeStoreProductDTO.class, restTemplate.getMessageConverters());
        FakeStoreProductDTO fakeStoreProductDTO = restTemplate.execute("https://fakestoreapi.com/products/" + id, HttpMethod.PUT, requestCallback, responseExtractor);
        return convertFakeStoreProductDTOtoProduct(fakeStoreProductDTO);
    }

    @Override
    public Product deleteProduct(Long id) {
        FakeStoreProductDTO fakeStoreProductDTO = restTemplate.exchange("https://fakestoreapi.com/products/" + id, HttpMethod.DELETE, null, FakeStoreProductDTO.class).getBody();
        return fakeStoreProductDTO != null ? convertFakeStoreProductDTOtoProduct(fakeStoreProductDTO) : null;
    }
}
