package com.example.productservice.modals;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Getter
@Setter
@Document(indexName = "products")
public class ProductDocument {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Double)
    private double price;

    // Constructors, Getters, and Setters

    public ProductDocument() {}

    public ProductDocument(String id, String title, String description, double price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
    }
}

