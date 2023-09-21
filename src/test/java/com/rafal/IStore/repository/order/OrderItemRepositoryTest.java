package com.rafal.IStore.repository.order;

import com.rafal.IStore.model.order.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    void testOrderItemRepository_Save(){
        //Given
        OrderItem orderItem = new OrderItem();

        //When
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);

        //Then
        assertNotNull(savedOrderItem);
    }

    @Test
    void testOrderItemRepository_GetAll(){
        //Given
        OrderItem orderItem = new OrderItem();

        orderItemRepository.save(orderItem);

        //When
        List<OrderItem> orderItems = orderItemRepository.findAll();

        //Then
        assertNotNull(orderItem);
        assertEquals(1, orderItems.size());
    }
}