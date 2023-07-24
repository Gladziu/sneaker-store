package com.rafal.IStore.controller;

import com.rafal.IStore.Busket;
import com.rafal.IStore.model.Item;
import com.rafal.IStore.repository.ItemRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class HomeController {

    private final ItemRepository itemRepository;
    private final Busket busket;

    @Autowired
    public HomeController(ItemRepository itemRepository, Busket busket) {
        this.itemRepository = itemRepository;
        this.busket = busket;
    }

    @GetMapping("/")
    public String home(Model model, HttpSession httpSession){
        model.addAttribute("items" ,itemRepository.findAll());
        return "home";
    }

    @GetMapping("/add/{itemId}")
    public String addItemToBasket(@PathVariable("itemId") Long itemId, Model model){

        Optional<Item> oItem = itemRepository.findById(itemId);
        if (oItem.isPresent()){
            Item item = oItem.get();
            busket.addItem(item);
        }
        model.addAttribute("items" ,itemRepository.findAll());
        return "home";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }
}
