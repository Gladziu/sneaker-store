package com.rafal.IStore.mapper;

import com.rafal.IStore.dto.OrderDto;
import com.rafal.IStore.model.busket.BusketItem;
import com.rafal.IStore.model.order.Order;
import com.rafal.IStore.model.order.OrderItem;
import com.rafal.IStore.service.busket.Busket;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class OrderMapper {


    public static Order mapToOrder(OrderDto orderDto, Busket busket){
        return Order.builder()
                .firstName(orderDto.getFirstName())
                .lastName(orderDto.getLastName())
                .address(orderDto.getAddress())
                .postCode(orderDto.getPostCode())
                .city(orderDto.getCity())
                .created(LocalDateTime.now())
                .sum(busket.getSum())
                .build();
    }

    public static List<OrderItem> mapToOrderItemList(Busket busket, Order order){
        List<OrderItem> orderItems = new ArrayList<>();
        for(BusketItem bItem : busket.getBusketItem()) {
            orderItems.add(new OrderItem(order.getOrderId(), bItem.getItemWithSize().getItem().getId(), bItem.getCounter(), bItem.getItemWithSize().getSize()));
        }
        return orderItems;
    }

}
