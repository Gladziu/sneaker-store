package com.rafal.IStore.order.controller;

import com.rafal.IStore.order.OrderDto;
import com.rafal.IStore.order.OrderViewService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sneaker-store/order")
public class OrderController {
    private final OrderViewService orderViewService;

    public OrderController(OrderViewService orderViewService) {
        this.orderViewService = orderViewService;
    }

    @GetMapping("/summary")
    public String summaryPage(Model model, Authentication authentication) {
        return orderViewService.summaryView(model, authentication);
    }

    @PostMapping("/save-order")
    public String saveOrder(@Valid OrderDto orderDto, BindingResult bindingResult, Model model, Authentication authentication) {
        return orderViewService.processSavingOrder(orderDto, bindingResult, model, authentication);
    }


    @GetMapping("/history")
    public String orderHistory(Model model, Authentication authentication) {
        orderViewService.populateOrderHistoryModel(model, authentication);
        return "orders";
    }
}
