package com.rafal.IStore.service.item;

import com.rafal.IStore.model.item.Item;

import java.math.BigDecimal;

public interface ItemService {
    public void deleteItem(Long itemId);
    public void editItem(Long itemId, BigDecimal newPrice, String newName, String newUrlImg);
    public Item findItemById(Long itemId);
}
