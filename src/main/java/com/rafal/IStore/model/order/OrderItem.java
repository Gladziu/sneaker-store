package com.rafal.IStore.model.order;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    private Long orderId;
    private Long itemId;
    private int amount;
    private int size;

    public OrderItem(Long orderId, Long itemId, int amount, int size) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.amount = amount;
        this.size = size;
    }
}
