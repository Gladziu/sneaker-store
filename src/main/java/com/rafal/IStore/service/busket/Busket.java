package com.rafal.IStore.service.busket;

import com.rafal.IStore.model.busket.BusketItem;
import com.rafal.IStore.model.item.Item;
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

    public void addItem(Item item){
        getBusketItemByItem(item).ifPresentOrElse(
                BusketItem::increaseCounter,
                () -> busketItem.add(new BusketItem(item))
        );
        recalulatePriceAndCounter();
    }

    public void removeItem(Item item){
        Optional<BusketItem> optionalBusketItem = getBusketItemByItem(item);
        if (optionalBusketItem.isPresent()){
            BusketItem bItem = optionalBusketItem.get();
            bItem.decreaseCounter();
            if(bItem.hasZeroItems()){
                removeAllItems(item);
            } else {
                recalulatePriceAndCounter();
            }
        }
    }

    public void removeAllItems(Item item) {
        busketItem.removeIf(i -> i.idEquals(item));
        recalulatePriceAndCounter();
    }


    private void recalulatePriceAndCounter(){
        sum = busketItem.stream().map(BusketItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        counter = busketItem.stream().mapToInt(BusketItem::getCounter)
                .reduce(0, Integer::sum);
    }

    private Optional<BusketItem> getBusketItemByItem(Item item){
        return busketItem.stream()
                .filter(i -> i.idEquals(item))
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
