package com.rafal.IStore.order;

import com.rafal.IStore.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
