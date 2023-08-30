package com.rafal.IStore.controller;

import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.model.order.Order;
import com.rafal.IStore.repository.ItemRepository;
import com.rafal.IStore.repository.order.OrderRepository;
import com.rafal.IStore.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final ItemService itemService;

    @GetMapping("/add-item")
    public String adminPage() { return "adminview/add-item"; }

    @PostMapping("/add-item")
    public String addItem(Item item){
        itemRepository.save(item);
        return "redirect:/sneaker-store/home";
    }

    @GetMapping("/show-orders")
    @ResponseBody
    public List<Order> showOrders(){ return orderRepository.findAll(); }

    @GetMapping("/delete/{itemId}")
    public String deleteItem(@PathVariable("itemId") Long itemId){
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
        itemService.editItem(item.getId(), item.getPrice(), item.getName(), item.getUrlImage());
        return "redirect:/sneaker-store/home";
    }
}
