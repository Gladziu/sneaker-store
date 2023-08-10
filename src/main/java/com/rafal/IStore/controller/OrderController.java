package com.rafal.IStore.controller;

import com.rafal.IStore.model.item.ItemOperation;
import com.rafal.IStore.dto.OrderDto;
import com.rafal.IStore.service.busket.BusketService;
import com.rafal.IStore.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sneaker-store/order")
public class OrderController {

    private final BusketService busketService;
    private final OrderService orderService;

    @Autowired
    public OrderController(BusketService busketService, OrderService orderService) {
        this.busketService = busketService;
        this.orderService = orderService;
    }

    @GetMapping("/busket")
    public String showBusket(){
        return "busket";
    }

    @GetMapping("/increase/{itemId}")
    public String increaseItem(@PathVariable("itemId") Long itemId){
        busketService.itemOperation(itemId, ItemOperation.INCREASE);
        return "busket";
    }

    @GetMapping("/decrease/{itemId}")
    public String decreaseItem(@PathVariable("itemId") Long itemId){
        busketService.itemOperation(itemId, ItemOperation.DECREASE);
        return "busket";
    }

    @GetMapping("/remove/{itemId}")
    public String removeItemFromBasket(@PathVariable("itemId") Long itemId){
        busketService.itemOperation(itemId, ItemOperation.REMOVE);
        return "busket";
    }

    @GetMapping("/summary")
    public String showSummary(){
        return "summary";
    }

    @PostMapping("/save-order")
    public String saveOrder(OrderDto orderDto) {
        orderService.saveOrder(orderDto);
        return "redirect:/sneaker-store/home";
    }


}
