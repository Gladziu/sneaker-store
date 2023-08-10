package com.rafal.IStore.controller;

import com.rafal.IStore.model.item.ItemOperation;
import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.model.order.Order;
import com.rafal.IStore.repository.ItemRepository;
import com.rafal.IStore.repository.order.OrderRepository;
import com.rafal.IStore.service.busket.BusketService;
import com.rafal.IStore.service.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final BusketService busketService;
    @Autowired
    public AdminController(ItemRepository itemRepository, OrderRepository orderRepository, ItemService itemService, BusketService busketService) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.itemService = itemService;
        this.busketService = busketService;
    }

    @GetMapping("/add-item")
    private String adminPage() {
        return "adminview/additem";
    }

    @PostMapping("/add-item")
    private String addItem(Item item){
        itemRepository.save(item);
        return "redirect:/sneaker-store/home";
    }

    @GetMapping("/show-orders")
    @ResponseBody
    public List<Order> showOrders(){
        return orderRepository.findAll();
    }

    @GetMapping("/delete/{itemId}")
    public String deleteItem(@PathVariable("itemId") Long itemId){
        busketService.itemOperation(itemId, ItemOperation.REMOVE);
        itemService.deleteItem(itemId);
        return "redirect:/sneaker-store/home";
    }

    @GetMapping("/edit-item/{itemId}")
    public String editItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findItemById(itemId);
        model.addAttribute("item", item);
        return "adminview/edititem";
    }

    @PostMapping("/edit-item")
    public String editItem(@ModelAttribute Item item) {
        itemService.updateItemPrice(item.getId(), item.getPrice(), item.getName(), item.getUrlImage());
        return "redirect:/sneaker-store/home";
    }
}
