package com.rafal.IStore.admin;

import com.rafal.IStore.item.ItemRepository;
import com.rafal.IStore.item.model.Item;
import com.rafal.IStore.shoppingbasket.BasketItemService;
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
        basketItemService.deleteBasketItems(findItemById(itemId));
        itemRepository.deleteById(itemId);
    }

    @Override
    public void editItem(Item item) {
        Optional<Item> existingOptionalItem = itemRepository.findById(item.getId());
        if (existingOptionalItem.isPresent()) {
            basketItemService.deleteBasketItems(item);
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
