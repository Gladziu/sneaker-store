package com.rafal.IStore.model.item;

import com.rafal.IStore.model.basket.BasketItem;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private String urlImage;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<BasketItem> basketItem = new ArrayList<>();

    public Item(String name, BigDecimal price, String urlImage) {
        this.name = name;
        this.price = price;
        this.urlImage = urlImage;
    }
}
