package com.rafal.IStore.controller;

import com.rafal.IStore.service.BusketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final BusketService busketService;

    @Autowired
    public OrderController(BusketService busketService) {
        this.busketService = busketService;
    }


    @GetMapping("/busket")
    public String showBusket(){
        return "busket";
    }

    @GetMapping("/increase/{itemId}")
    public String increaseItem(@PathVariable("itemId") Long itemId){
        busketService.addItemToBusket(itemId);
        return "busket";
    }

    @GetMapping("/decrease/{itemId}")
    public String decreaseItem(@PathVariable("itemId") Long itemId){
        busketService.removeItemFromBusket(itemId);
        return "busket";
    }
}
