package com.rafal.IStore.controller;

import com.rafal.IStore.ItemOperation;
import com.rafal.IStore.service.BusketService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    private final BusketService busketService;

    @Autowired
    public HomeController(BusketService busketService) {
        this.busketService = busketService;
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession httpSession){
        model.addAttribute("items" ,busketService.getAllItems());
        return "home";
    }

    @GetMapping("/add/{itemId}")
    public String addItemToBasket(@PathVariable("itemId") Long itemId, Model model){
        busketService.itemOperation(itemId, ItemOperation.INCREASE);
        model.addAttribute("items" ,busketService.getAllItems());
        return "home";
    }
}
