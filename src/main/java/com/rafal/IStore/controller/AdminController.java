package com.rafal.IStore.controller;

import com.rafal.IStore.model.Item;
import com.rafal.IStore.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ItemRepository itemRepository;

    @Autowired
    public AdminController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    private String adminPage() {
        return "adminview/additem";
    }

    @PostMapping
    private String addItem(Item item){
        itemRepository.save(item);
        return "redirect:/";
    }
}
