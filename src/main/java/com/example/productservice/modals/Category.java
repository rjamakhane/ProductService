package com.example.productservice.modals;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity(name="Category")
public class Category extends BaseModal {
//    @OneToMany(mappedBy = "category", cascade = {CascadeType.REMOVE})
//    List<Product> products;
    private String title;
}

