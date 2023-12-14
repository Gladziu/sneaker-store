package com.rafal.IStore.model.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
