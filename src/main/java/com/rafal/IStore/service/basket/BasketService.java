package com.rafal.IStore.service.basket;

import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.model.item.ItemOperation;

import java.util.List;

public interface BasketService {
    public List<Item> getAllItems();
    public boolean isBasketEmpty();
    public void itemOperation(Long itemId, int size, ItemOperation itemOperation);
}
