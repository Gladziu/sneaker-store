package com.rafal.IStore.model.basket;

import com.rafal.IStore.model.item.ItemWithSize;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BasketItem {
    private ItemWithSize itemWithSize;
    private int counter;
    private BigDecimal price;

    public BasketItem(ItemWithSize itemWithSize) {
        this.itemWithSize = itemWithSize;
        this.counter = 1;
        this.price = itemWithSize.getItem().getPrice();
    }

    public void increaseCounter() {
        counter++;
        recalculation();
    }

    public void decreaseCounter() {
        if (counter > 0) {
            counter--;
            recalculation();
        }
    }

    private void recalculation() {
        price = itemWithSize.getItem().getPrice().multiply(new BigDecimal(counter));
    }

    public boolean hasZeroItems() {
        return counter == 0;
    }

    public boolean idEquals(ItemWithSize itemWithSize) {
        return this.itemWithSize.getItem().getId().equals(itemWithSize.getItem().getId()) && this.itemWithSize.getSize() == itemWithSize.getSize();
    }
}
