package com.rafal.IStore.controller;

import com.rafal.IStore.model.item.ItemOperation;
import com.rafal.IStore.service.basket.BasketService;
import com.rafal.IStore.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/sneaker-store")
public class HomeController {

    private final BasketService basketService;
    private final UserService userService;

    public HomeController(BasketService basketService, UserService userService) {
        this.basketService = basketService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model,
                       HttpSession httpSession,
                       Authentication authentication) {
        model.addAttribute("items", basketService.getAllItems());
        if (userService.isAdmin(authentication)) {
            return "adminview/admin-home";
        }
        return "home";
    }

    @GetMapping("/add/{itemId}")
    public String addItemToBasket(
            @PathVariable("itemId") Long itemId,
            @RequestParam("size") int selectedSize,
            Model model) {
        basketService.itemOperation(itemId, selectedSize, ItemOperation.INCREASE);
        model.addAttribute("items", basketService.getAllItems());
        return "redirect:/sneaker-store/home";
    }
}
