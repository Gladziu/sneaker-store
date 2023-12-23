package com.rafal.IStore.order.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "order_history_id")
    private OrderHistory orderHistory;
    private String name;
    private BigDecimal price;
    private int quantity;
    private int size;

    public OrderItem(OrderHistory orderHistory, String name, BigDecimal price, int quantity, int size) {
        this.orderHistory = orderHistory;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
    }
}
