package com.rafal.IStore.service.item;

import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.repository.ItemRepository;
import com.rafal.IStore.service.basket.BasketItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final BasketItemService basketItemService;

    public ItemServiceImpl(ItemRepository itemRepository, BasketItemService basketItemService) {
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
    //TODO: góra do adminService

    //TODO: to zostawic w item
    @Override
    public Item findItemById(Long itemId) {
        return itemRepository.findById(itemId).orElse(null);
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}