package com.rafal.IStore.controller;

import com.rafal.IStore.model.item.ItemOperation;
import com.rafal.IStore.model.user.User;
import com.rafal.IStore.service.busket.BusketService;
import com.rafal.IStore.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final BusketService busketService;
    private final UserService userService;

    @Autowired
    public HomeController(BusketService busketService, UserService userService) {
        this.busketService = busketService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model,
                       HttpSession httpSession,
                       Authentication authentication){
        model.addAttribute("items" ,busketService.getAllItems());
        User user = userService.getCurrentUser(authentication);
        if (userService.roleMatching("ROLE_ADMIN", user.getEmail())){
            return "adminview/adminhome";
        }
        return "home";
    }

    @GetMapping("/add/{itemId}")
    public String addItemToBasket(
            @PathVariable("itemId") Long itemId,
            @RequestParam("size") int selectedSize,
            Model model){
        busketService.itemOperation(itemId, selectedSize, ItemOperation.INCREASE);
        model.addAttribute("items" ,busketService.getAllItems());
        return "redirect:/sneaker-store/home";
    }
}
