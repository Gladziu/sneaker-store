package com.rafal.sneakerstoreapp.shoppingbasket.model;

import com.rafal.sneakerstoreapp.item.model.Item;
import com.rafal.sneakerstoreapp.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private int size;
    private int counter;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
