package com.rafal.IStore.controller;

import com.rafal.IStore.dto.UserDto;
import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.service.item.ItemService;
import com.rafal.IStore.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ItemService itemService;

    public AdminController(UserService userService, ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }

    @GetMapping("/add-item")
    public String adminPage() {
        return "adminview/add-item";
    }

    @PostMapping("/add-item")
    public String addItem(Item item) {
        itemService.addItem(item);
        return "redirect:/sneaker-store/home";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "adminview/users-list";
    }

    @GetMapping("/delete/{itemId}")
    public String deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.deleteItem(itemId);
        return "redirect:/sneaker-store/home";
    }

    @GetMapping("/edit-item/{itemId}")
    public String editItemForm(@PathVariable("itemId") Long itemId,
                               Model model) {
        Item item = itemService.findItemById(itemId);
        model.addAttribute("item", item);
        return "adminview/edit-item";
    }

    @PostMapping("/edit-item")
    public String editItem(Item item) {
        itemService.editItem(item);
        return "redirect:/sneaker-store/home";
    }
}
