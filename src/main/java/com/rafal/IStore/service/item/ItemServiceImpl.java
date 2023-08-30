package com.rafal.IStore.service.item;

import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.model.item.ItemOperation;
import com.rafal.IStore.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void deleteItem(Long itemId) { itemRepository.deleteById(itemId); }

    @Override
    public void editItem(Long itemId, BigDecimal newPrice, String newName, String newUrlImg) {
        Optional<Item> oItem = itemRepository.findById(itemId);
        if (oItem.isPresent()) {
            Item item = oItem.get();
            item.setPrice(newPrice);
            item.setName(newName);
            item.setUrlImage(newUrlImg);
            itemRepository.save(item);
        }
    }

    @Override
    public Item findItemById(Long itemId) { return itemRepository.findById(itemId).orElse(null);
    }
}