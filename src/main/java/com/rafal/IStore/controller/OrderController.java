package com.rafal.IStore.controller;

import com.rafal.IStore.dto.OrderDto;
import com.rafal.IStore.model.item.ItemOperation;
import com.rafal.IStore.model.user.User;
import com.rafal.IStore.service.busket.BusketService;
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

@Controller
@RequestMapping("/sneaker-store/order")
public class OrderController {

    private final BusketService busketService;
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(BusketService busketService, OrderService orderService, UserService userService) {
        this.busketService = busketService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/busket")
    public String showBusket(){
        return "busket";
    }

    @GetMapping("/increase/{itemId}")
    public String increaseItem(@PathVariable("itemId") Long itemId){
        busketService.itemOperation(itemId, ItemOperation.INCREASE);
        return "busket";
    }

    @GetMapping("/decrease/{itemId}")
    public String decreaseItem(@PathVariable("itemId") Long itemId){
        busketService.itemOperation(itemId, ItemOperation.DECREASE);
        return "busket";
    }

    @GetMapping("/remove/{itemId}")
    public String removeItemFromBasket(@PathVariable("itemId") Long itemId){
        busketService.itemOperation(itemId, ItemOperation.REMOVE);
        return "busket";
    }

    @GetMapping("/summary")
    public String showSummary(Model model, Authentication authentication){
        if(busketService.isBusketEmpty()){
            model.addAttribute("busketIsEmpty", "Busket is empty!");
            return "busket";
        }
        User user = userService.getCurrentUser(authentication);
        String[] userSplited = user.getName().split(" ");
        model.addAttribute("name", userSplited[0]);
        model.addAttribute("surname", userSplited[1]);
        model.addAttribute("orderDto", new OrderDto());
        return "summary";
    }

    @PostMapping("/save-order")
    public String saveOrder(@Valid OrderDto orderDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "summary"; // Powrót do widoku z formularzem i wyświetleniem błędów
        }

        orderService.saveOrder(orderDto);
        return "redirect:/sneaker-store/home";
    }


}
