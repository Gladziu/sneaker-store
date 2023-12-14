package com.rafal.IStore.repository.order;

import com.rafal.IStore.model.order.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderHistory, Long> {
    List<OrderHistory> findByEmail(String email);
}
