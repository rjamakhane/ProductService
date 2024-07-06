package com.example.productservice.modals;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Rating extends BaseModal{
    private int rate;
    private int count;
    public Rating() {
    }

    public Rating(int rate, int count) {
        this.rate = rate;
        this.count = count;
    }
}
