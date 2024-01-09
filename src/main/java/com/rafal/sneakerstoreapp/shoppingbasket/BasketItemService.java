package com.rafal.sneakerstoreapp.shoppingbasket;

import com.rafal.sneakerstoreapp.item.model.Item;
import com.rafal.sneakerstoreapp.item.model.ItemWithSize;
import com.rafal.sneakerstoreapp.shoppingbasket.model.BasketItem;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface BasketItemService {
    void addItem(ItemWithSize itemWithSize, UUID userId);

    void removeItem(ItemWithSize itemWithSize, UUID userId);

    void removeAllIdenticalItems(ItemWithSize itemWithSize, UUID userId);

    void clearBasket(UUID userId);

    void removeItemFromEachBasket(Item item);

    List<BasketItem> getBasketItems(Authentication authentication);
}
