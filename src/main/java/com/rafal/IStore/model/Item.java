package com.rafal.IStore.model;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private BigDecimal price;
    private String urlImage;

    public Item(String name, BigDecimal price, String urlImage) {
        this.name = name;
        this.price = price;
        this.urlImage = urlImage;
    }
}
