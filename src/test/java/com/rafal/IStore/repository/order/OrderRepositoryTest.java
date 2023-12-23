/*
package com.rafal.IStore.repository.order;

import com.rafal.IStore.model.order.Order;
import com.rafal.IStore.order.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testFindByEmail(){
        //Given
        String email = "test@example.com";
        Order order = Order.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .address("ul. Testowa 123")
                .postCode("12-345")
                .city("Testowo")
                .created(LocalDateTime.now())
                .sum(BigDecimal.valueOf(100.0))
                .email("test@example.com")
                .build();

        orderRepository.save(order);

        //When
        List<Order> orders = orderRepository.findAllByEmail(email);

        //Then
        assertNotNull(orders);
        assertEquals(email, orders.get(0).getEmail());
    }
}*/
