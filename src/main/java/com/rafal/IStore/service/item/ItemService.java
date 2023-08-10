package com.rafal.IStore.service.item;

import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    public void updateItemPrice(Long itemId, BigDecimal newPrice, String newName, String newUrlImg) {
        Item item = itemRepository.findById(itemId).orElse(null);
        if (item != null) {
            item.setPrice(newPrice);
            item.setName(newName);
            item.setUrlImage(newUrlImg);
            itemRepository.save(item);
        }
    }

    public Item findItemById(Long itemId) { return itemRepository.findById(itemId).orElse(null);
    }
}