package com.rafal.IStore.service.basket;

import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.model.item.ItemOperation;

import java.util.List;

public interface BasketService {
    List<Item> getAllItems();
    boolean isBasketEmpty();
    void itemOperation(Long itemId, int size, ItemOperation itemOperation);
}
