package com.rafal.IStore.service.busket;

import com.rafal.IStore.model.busket.BusketItem;
import com.rafal.IStore.model.item.Item;
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
public class Busket {
    private List<BusketItem> busketItem = new ArrayList<>();
    private int counter = 0;
    private BigDecimal sum = BigDecimal.ZERO;

    public void addItem(ItemWithSize itemWithSize){
        getBusketItemByItem(itemWithSize).ifPresentOrElse(
                BusketItem::increaseCounter,
                () -> busketItem.add(new BusketItem(itemWithSize)
        ));
        recalulatePriceAndCounter();
    }

    public void removeItem(ItemWithSize itemWithSize){
        Optional<BusketItem> optionalBusketItem = getBusketItemByItem(itemWithSize);
        if (optionalBusketItem.isPresent()){
            BusketItem bItem = optionalBusketItem.get();
            bItem.decreaseCounter();
            if(bItem.hasZeroItems()){
                removeAllItems(itemWithSize);
            } else {
                recalulatePriceAndCounter();
            }
        }
    }

    public void removeAllItems(ItemWithSize itemWithSize) {
        busketItem.removeIf(i -> i.idEquals(itemWithSize));
        recalulatePriceAndCounter();
    }


    private void recalulatePriceAndCounter(){
        sum = busketItem.stream().map(BusketItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        counter = busketItem.stream().mapToInt(BusketItem::getCounter)
                .reduce(0, Integer::sum);
    }

    private Optional<BusketItem> getBusketItemByItem(ItemWithSize itemWithSize){
        return busketItem.stream()
                .filter(itemInBasket -> itemInBasket.idEquals(itemWithSize))
                .findFirst();
    }

    public void clearBusket(){
        busketItem.clear();
        counter = 0;
        sum = BigDecimal.ZERO;
    }

    public boolean isBusketQuantityZero(){
        return sum.equals(BigDecimal.ZERO);
    }
}
