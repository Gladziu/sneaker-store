package com.rafal.IStore.model.busket;

import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.model.item.ItemWithSize;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BusketItem {
    private ItemWithSize itemWithSize;
    private int counter;
    private BigDecimal price;

    public BusketItem(ItemWithSize itemWithSize) {
        this.itemWithSize = itemWithSize;
        this.counter = 1;
        this.price = itemWithSize.getItem().getPrice();
    }

    public void increaseCounter(){
        counter++;
        recalcalculate();
    }

    public void decreaseCounter(){
        if(counter > 0){
            counter--;
            recalcalculate();
        }
    }

    private void recalcalculate(){
        price = itemWithSize.getItem().getPrice().multiply(new BigDecimal(counter));
    }

    public boolean hasZeroItems(){
        return counter == 0;
    }

    public boolean idEquals(ItemWithSize itemWithSize){
        return this.itemWithSize.getItem().getId().equals(itemWithSize.getItem().getId()) && this.itemWithSize.getSize() == itemWithSize.getSize();
    }
}
