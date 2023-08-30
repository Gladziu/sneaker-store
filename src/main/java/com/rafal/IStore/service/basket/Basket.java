package com.rafal.IStore.service.basket;

import com.rafal.IStore.model.basket.BasketItem;
import com.rafal.IStore.model.item.ItemWithSize;

import java.math.BigDecimal;
import java.util.List;

public interface Basket {
    public void addItem(ItemWithSize itemWithSize);
    public void removeItem(ItemWithSize itemWithSize);
    public void removeAllItems(ItemWithSize itemWithSize);
    public void clearBasket();
    public boolean isBasketQuantityZero();
    List<BasketItem> getBasketItem();
    BigDecimal getSum();
}
