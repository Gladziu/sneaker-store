package com.rafal.sneakerstoreapp.shoppingbasket.model;

import com.rafal.sneakerstoreapp.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Setter
@Getter
public class BasketInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private BigDecimal sum;
    private int quantity;
}
