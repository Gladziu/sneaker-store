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
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/sneaker-store/order")
public class OrderController {

    private final BasketService basketService;
    private final OrderService orderService;
    private final UserService userService;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(BasketService basketService, OrderService orderService, UserService userService, OrderRepository orderRepository) {
        this.basketService = basketService;
        this.orderService = orderService;
        this.userService = userService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/basket")
    public String showBasket(){
        return "basket";
    }

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
        User user = userService.getCurrentUser(authentication);
        String fullName = user.getName();
        model.addAttribute("name", fullName.split(" ")[0]);
        model.addAttribute("surname", fullName.split(" ")[1]);
        model.addAttribute("orderDto", new OrderDto());
        return "summary";
    }

    @PostMapping("/save-order")
    public String saveOrder(@Valid OrderDto orderDto,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "summary";
        }
        orderService.saveOrder(orderDto);
        return "thanks-for-shopping";
    }


    @GetMapping("/history")
    public String orderHistory(Model model,
                               Authentication authentication){
        User user = userService.getCurrentUser(authentication);
        String fullName = user.getName();
        List<Order> currentUserOrders = orderRepository.findByFirstNameAndLastName(fullName.split(" ")[0], fullName.split(" ")[1]);
        model.addAttribute("orders", currentUserOrders);
        model.addAttribute("items", basketService.getAllItems());
        return "orders";
    }
}
