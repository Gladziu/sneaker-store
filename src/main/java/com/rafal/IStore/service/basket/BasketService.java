package com.rafal.IStore.service.basket;

import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.model.item.ItemOperation;
import com.rafal.IStore.model.item.ItemWithSize;
import com.rafal.IStore.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasketService {

    private final ItemRepository itemRepository;
    private final Basket basket;


    @Autowired
    public BasketService(ItemRepository itemRepository, Basket basket) {
        this.itemRepository = itemRepository;
        this.basket = basket;

    }

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public boolean isBasketEmpty(){ return basket.isBasketQuantityZero(); }

    public void itemOperation(Long itemId, int size, ItemOperation itemOperation) {
        Optional<Item> oItem = itemRepository.findById(itemId);
        if (oItem.isPresent()){
            Item item = oItem.get();
            ItemWithSize itemWithSize = new ItemWithSize(item, size);
            switch (itemOperation){
                case INCREASE -> basket.addItem(itemWithSize);
                case DECREASE -> basket.removeItem(itemWithSize);
                case REMOVE -> basket.removeAllItems(itemWithSize);
                default -> throw new IllegalArgumentException();
            }
        }
    }


}
