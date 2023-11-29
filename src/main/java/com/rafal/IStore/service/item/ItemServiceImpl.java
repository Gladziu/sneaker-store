package com.rafal.IStore.service.item;

import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Override
    public void editItem(Item item) {
        Optional<Item> existingOptionalItem = itemRepository.findById(item.getId());
        if (existingOptionalItem.isPresent()) {
            itemRepository.save(item);
        }
    }

    @Override
    public Item findItemById(Long itemId) {
        return itemRepository.findById(itemId).orElse(null);
    }

    @Override
    public void addItem(Item item) {
        itemRepository.save(item);
    }
}