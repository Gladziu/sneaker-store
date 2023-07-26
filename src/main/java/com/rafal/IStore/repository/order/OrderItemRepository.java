package com.rafal.IStore.repository.order;

import com.rafal.IStore.model.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
