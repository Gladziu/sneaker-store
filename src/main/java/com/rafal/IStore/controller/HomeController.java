package com.rafal.IStore.controller;

import com.rafal.IStore.repository.ItemRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ItemRepository itemRepository;

    @Autowired
    public HomeController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    @GetMapping("/")
    public String home(Model model, HttpSession httpSession){
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
