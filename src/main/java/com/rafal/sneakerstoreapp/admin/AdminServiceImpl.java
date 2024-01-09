package com.rafal.sneakerstoreapp.admin;

import com.rafal.sneakerstoreapp.item.ItemRepository;
import com.rafal.sneakerstoreapp.item.model.Item;
import com.rafal.sneakerstoreapp.shoppingbasket.BasketItemService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class AdminServiceImpl  implements AdminService{
    private final ItemRepository itemRepository;
    private final BasketItemService basketItemService;

    public AdminServiceImpl(ItemRepository itemRepository, BasketItemService basketItemService) {
        this.itemRepository = itemRepository;
        this.basketItemService = basketItemService;
    }

    @Override
    public void deleteItem(Long itemId) {
        basketItemService.removeItemFromEachBasket(findItemById(itemId));
        itemRepository.deleteById(itemId);
    }

    @Override
    public void editItem(Item item) {
        Optional<Item> existingOptionalItem = itemRepository.findById(item.getId());
        if (existingOptionalItem.isPresent()) {
            basketItemService.removeItemFromEachBasket(item);
            itemRepository.save(item);
        }
    }

    @Override
    public void addItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public Item findItemById(Long itemId) {
        return itemRepository.findById(itemId).orElse(null);
    }

}
