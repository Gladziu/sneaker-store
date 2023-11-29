package com.rafal.IStore.service.basket;

import com.rafal.IStore.model.basket.BasketItem;
import com.rafal.IStore.model.item.ItemWithSize;

import java.math.BigDecimal;
import java.util.List;

public interface Basket {
    void addItem(ItemWithSize itemWithSize);
    void removeItem(ItemWithSize itemWithSize);
    void removeAllItems(ItemWithSize itemWithSize);
    void clearBasket();
    boolean isBasketQuantityZero();
    List<BasketItem> getBasketItem();
    BigDecimal getSum();
}
