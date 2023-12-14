package com.rafal.IStore.mapper;

import com.rafal.IStore.dto.OrderDto;
import com.rafal.IStore.model.basket.BasketItem;
import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.model.order.OrderHistory;
import com.rafal.IStore.model.order.OrderItem;
import com.rafal.IStore.model.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {


    public static OrderHistory mapToOrder(OrderDto orderDto, User user, BigDecimal sum) {
        return OrderHistory.builder()
                .firstName(orderDto.getFirstName())
                .lastName(orderDto.getLastName())
                .address(orderDto.getAddress())
                .postCode(orderDto.getPostCode())
                .city(orderDto.getCity())
                .created(LocalDateTime.now())
                .sum(sum)
                .email(user.getEmail())
                .build();
    }

    public static List<OrderItem> mapToOrderItemList(OrderHistory orderHistory, List<BasketItem> basketItemItems) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (BasketItem basketItem : basketItemItems) {
            Item item = basketItem.getItem();
            String name = item.getName();
            BigDecimal price = item.getPrice();
            int quantity = basketItem.getCounter();
            int size = basketItem.getSize();
            orderItems.add(new OrderItem(orderHistory, name, price, quantity, size));
        }
        return orderItems;
    }

}
