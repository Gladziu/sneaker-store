package com.rafal.IStore.order;

import com.rafal.IStore.order.model.OrderHistory;
import com.rafal.IStore.order.model.OrderItem;
import com.rafal.IStore.shoppingbasket.model.BasketItem;
import com.rafal.IStore.item.model.Item;
import com.rafal.IStore.user.UserDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class OrderMapper {

    public static OrderHistory mapToOrderHistory(OrderDto orderDto, UserDto userDto, BigDecimal sum) {
        return OrderHistory.builder()
                .firstName(orderDto.getFirstName())
                .lastName(orderDto.getLastName())
                .address(orderDto.getAddress())
                .postCode(orderDto.getPostCode())
                .city(orderDto.getCity())
                .created(LocalDateTime.now())
                .sum(sum)
                .email(userDto.getEmail())
                .build();
    }

    public static List<OrderItem> mapToOrderItems(OrderHistory orderHistory, List<BasketItem> basketItemItems) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (BasketItem basketItem : basketItemItems) {
            OrderItem orderedItem = fillWithOrderedItemDetails(orderHistory, basketItem);
            orderItems.add(orderedItem);
        }
        return orderItems;
    }

    private static OrderItem fillWithOrderedItemDetails(OrderHistory orderHistory, BasketItem basketItem) {
        Item item = basketItem.getItem();
        String name = item.getName();
        BigDecimal price = item.getPrice();
        int quantity = basketItem.getCounter();
        int size = basketItem.getSize();
        return new OrderItem(orderHistory, name, price, quantity, size);
    }

}
