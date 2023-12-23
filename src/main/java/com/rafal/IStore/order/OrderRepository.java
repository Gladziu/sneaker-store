package com.rafal.IStore.order;

import com.rafal.IStore.order.model.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
interface OrderRepository extends JpaRepository<OrderHistory, UUID> {
    List<OrderHistory> findAllByEmail(String email);
}
