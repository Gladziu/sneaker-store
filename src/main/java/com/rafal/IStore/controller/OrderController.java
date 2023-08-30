package com.rafal.IStore.controller;

import com.rafal.IStore.dto.OrderDto;
import com.rafal.IStore.model.item.ItemOperation;
import com.rafal.IStore.model.order.Order;
import com.rafal.IStore.model.user.User;
import com.rafal.IStore.repository.order.OrderRepository;
import com.rafal.IStore.service.basket.BasketService;
import com.rafal.IStore.service.order.OrderService;
import com.rafal.IStore.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sneaker-store/order")
public class OrderController {

    private final BasketService basketService;
    private final OrderService orderService;
    private final UserService userService;
    private final OrderRepository orderRepository;

    @GetMapping("/basket")
    public String showBasket(){ return "basket"; }

    @GetMapping("/increase/{itemId}/{size}")
    public String increaseItem(@PathVariable("itemId") Long itemId,
                               @PathVariable("size") int size){
        basketService.itemOperation(itemId, size, ItemOperation.INCREASE);
        return "basket";
    }

    @GetMapping("/decrease/{itemId}/{size}")
    public String decreaseItem(@PathVariable("itemId") Long itemId,
                               @PathVariable("size") int size){
        basketService.itemOperation(itemId, size, ItemOperation.DECREASE);
        return "basket";
    }

    @GetMapping("/remove/{itemId}/{size}")
    public String removeItemFromBasket(@PathVariable("itemId") Long itemId,
                                       @PathVariable("size") int size){
        basketService.itemOperation(itemId, size, ItemOperation.REMOVE);
        return "basket";
    }

    @GetMapping("/summary")
    public String showSummary(Model model,
                              Authentication authentication){
        if(basketService.isBasketEmpty()){
            model.addAttribute("basketIsEmpty", "Basket is empty!");
            return "basket";
        }
        String userFullName = userService.getCurrentUser(authentication).getName();
        model.addAttribute("name", userFullName.split(" ")[0]);
        model.addAttribute("surname", userFullName.split(" ")[1]);
        model.addAttribute("orderDto", new OrderDto());
        return "summary";
    }

    @PostMapping("/save-order")
    public String saveOrder(@Valid OrderDto orderDto,
                            BindingResult bindingResult,
                            Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "summary";
        }
        User user = userService.getCurrentUser(authentication);
        orderService.saveOrder(orderDto, user.getEmail());
        return "thanks-for-shopping";
    }


    @GetMapping("/history")
    public String orderHistory(Model model,
                               Authentication authentication){
        User user = userService.getCurrentUser(authentication);
        List<Order> currentUserOrders = orderRepository.findByEmail(user.getEmail());
        model.addAttribute("orders", currentUserOrders);
        model.addAttribute("items", basketService.getAllItems());
        return "orders";
    }
}
