package com.rafal.IStore.service.basket;

import com.rafal.IStore.model.basket.BasketItem;
import com.rafal.IStore.model.item.ItemWithSize;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
public class BasketImpl implements Basket {
    private final List<BasketItem> basketItem = new ArrayList<>();
    private int counter = 0;
    private BigDecimal sum = BigDecimal.ZERO;

    @Override
    public void addItem(ItemWithSize itemWithSize) {
        getBasketItemByItem(itemWithSize).ifPresentOrElse(
                BasketItem::increaseCounter,
                () -> basketItem.add(new BasketItem(itemWithSize)
                ));
        recalculatePriceAndCounter();
    }

    @Override
    public void removeItem(ItemWithSize itemWithSize) {
        Optional<BasketItem> optionalBasketItem = getBasketItemByItem(itemWithSize);
        if (optionalBasketItem.isPresent()) {
            BasketItem bItem = optionalBasketItem.get();
            bItem.decreaseCounter();
            if (bItem.hasZeroItems()) {
                removeAllItems(itemWithSize);
            } else {
                recalculatePriceAndCounter();
            }
        }
    }

    @Override
    public void removeAllItems(ItemWithSize itemWithSize) {
        basketItem.removeIf(i -> i.idEquals(itemWithSize));
        recalculatePriceAndCounter();
    }

    @Override
    public void clearBasket() {
        basketItem.clear();
        counter = 0;
        sum = BigDecimal.ZERO;
    }

    @Override
    public boolean isBasketQuantityZero() {
        return sum.equals(BigDecimal.ZERO);
    }

    private void recalculatePriceAndCounter() {
        sum = basketItem.stream().map(BasketItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        counter = basketItem.stream().mapToInt(BasketItem::getCounter)
                .reduce(0, Integer::sum);
    }

    private Optional<BasketItem> getBasketItemByItem(ItemWithSize itemWithSize) {
        return basketItem.stream()
                .filter(itemInBasket -> itemInBasket.idEquals(itemWithSize))
                .findFirst();
    }
}
