package com.rafal.IStore.service.item;

import com.rafal.IStore.model.item.Item;

import java.util.List;


public interface ItemService {
    void deleteItem(Long itemId);

    void editItem(Item item);

    Item findItemById(Long itemId);

    void addItem(Item item);

    List<Item> getAllItems();
}
