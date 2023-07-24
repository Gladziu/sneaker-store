package com.rafal.IStore;

import com.rafal.IStore.model.Item;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;

import java.math.BigDecimal;

@Getter
@Setter
public class BusketItem {
    private Item item;
    private int counter;
    private BigDecimal price;

    public BusketItem(Item item) {
        this.item = item;
        this.counter = 1;
        this.price = item.getPrice();
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
        price = item.getPrice().multiply(new BigDecimal(counter));
    }

    public boolean hasZeroItems(){
        return counter == 0;
    }

    public boolean idEquals(Item item){
        return this.item.getId().equals(item.getId());
    }
}
