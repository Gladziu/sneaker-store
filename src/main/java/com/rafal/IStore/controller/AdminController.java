package com.rafal.IStore.controller;

import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.service.item.ItemService;
import com.rafal.IStore.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final String REDIRECT_TO_HOME_PAGE = "redirect:/sneaker-store/home";
    private final UserService userService;
    private final ItemService itemService;

    public AdminController(UserService userService, ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }

    @GetMapping("/add-item")
    public String addItemPage() {
        return "adminview/add-item";
    }

    @PostMapping("/add-item")
    public String addItem(Item item) {
        itemService.addItem(item);
        return REDIRECT_TO_HOME_PAGE;
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "adminview/users-list";
    }

    @GetMapping("/edit-item/{itemId}")
    public String editItemPage(@PathVariable("itemId") Long itemId, Model model) {
        model.addAttribute("item", itemService.findItemById(itemId));
        return "adminview/edit-item";
    }

   @DeleteMapping("/delete/{itemId}")
    public String deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.deleteItem(itemId);
        return REDIRECT_TO_HOME_PAGE;
    }

    @PutMapping("/edit-item")
    public String editItem(Item item) {
        itemService.editItem(item);
        return REDIRECT_TO_HOME_PAGE;
    }
}
