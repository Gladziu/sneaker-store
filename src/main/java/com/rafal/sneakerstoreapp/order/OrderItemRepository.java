package com.rafal.sneakerstoreapp.order;

import com.rafal.sneakerstoreapp.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
