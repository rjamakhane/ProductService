package com.example.productservice.modals;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity(name="Product")
public class Product extends BaseModal implements Serializable {
    private String title;
    private String description;
    private double price;
//    @ManyToOne(cascade = {CascadeType.ALL}) //all operation like create update delete
    @ManyToOne(cascade = {CascadeType.PERSIST}) //only create operation
    private Category category;
    private String image;

    @ManyToOne
    private Rating rating;

    public Product() {
    }

}
