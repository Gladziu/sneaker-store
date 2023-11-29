package com.rafal.IStore.mapper;

import com.rafal.IStore.dto.OrderDto;
import com.rafal.IStore.model.basket.BasketItem;
import com.rafal.IStore.model.order.Order;
import com.rafal.IStore.model.order.OrderItem;
import com.rafal.IStore.service.basket.Basket;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static Order mapToOrder(OrderDto orderDto, Basket basket, String userEmail) {
        return Order.builder()
                .firstName(orderDto.getFirstName())
                .lastName(orderDto.getLastName())
                .address(orderDto.getAddress())
                .postCode(orderDto.getPostCode())
                .city(orderDto.getCity())
                .created(LocalDateTime.now())
                .sum(basket.getSum())
                .email(userEmail)
                .build();
    }

    public static List<OrderItem> mapToOrderItemList(Basket basket, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (BasketItem basketItem : basket.getBasketItem()) {
            Long orderId = order.getOrderId();
            Long itemId = basketItem.getItemWithSize().getItem().getId();
            int quantity = basketItem.getCounter();
            int size = basketItem.getItemWithSize().getSize();
            orderItems.add(new OrderItem(orderId, itemId, quantity, size));
        }
        return orderItems;
    }

}
