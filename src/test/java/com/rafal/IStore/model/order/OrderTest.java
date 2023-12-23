/*
package com.rafal.IStore.model.order;

import com.rafal.IStore.order.model.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderTest {
    private Order order;

    @BeforeEach
    public void setUp(){
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem());
        order = Order.builder()
                .orderId(1L)
                .firstName("Jan")
                .lastName("Kowalski")
                .address("address")
                .postCode("postcode")
                .city("city")
                .created(LocalDateTime.now())
                .sum(new BigDecimal("100.00"))
                .email("test@example.com")
                .orderItems(orderItems)
                .build();
    }

    @Test
    void getOrderId() {
        assertEquals(1L, order.getOrderId());
    }

    @Test
    void getFirstName() {
        assertEquals("Jan", order.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Kowalski", order.getLastName());
    }

    @Test
    void getAddress() {
        assertEquals("address", order.getAddress());
    }

    @Test
    void getPostCode() {
        assertEquals("postcode", order.getPostCode());
    }

    @Test
    void getCity() {
        assertEquals("city", order.getCity());
    }

    @Test
    void getCreated() {
        assertNotNull(order.getCreated());
    }

    @Test
    void getSum() {
        assertEquals(new BigDecimal("100.00"), order.getSum());
    }

    @Test
    void getEmail() {
        assertEquals("test@example.com", order.getEmail());
    }

    @Test
    void getOrderItems() {
        assertEquals(1 ,order.getOrderItems().size());
    }
}
*/
