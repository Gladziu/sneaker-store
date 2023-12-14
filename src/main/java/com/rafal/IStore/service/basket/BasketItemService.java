package com.rafal.IStore.service.basket;

import com.rafal.IStore.model.basket.BasketItem;
import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.model.item.ItemWithSize;
import com.rafal.IStore.model.user.User;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.List;

public interface BasketItemService {
    void addItem(ItemWithSize itemWithSize, User user);

    void removeItem(ItemWithSize itemWithSize, User user);

    void removeAllItems(ItemWithSize itemWithSize, User user);

    void clearBasket(User user);

    void deleteBasketItems(Item item);

    List<BasketItem> getBasketItems(Authentication authentication);
}
