package com.rafal.IStore.service;

import com.rafal.IStore.Busket;
import com.rafal.IStore.ItemOperation;
import com.rafal.IStore.model.Item;
import com.rafal.IStore.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusketService {

    private final ItemRepository itemRepository;
    private final Busket busket;

    @Autowired
    public BusketService(ItemRepository itemRepository, Busket busket) {
        this.itemRepository = itemRepository;
        this.busket = busket;
    }

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }


    public void itemOperation(Long itemId, ItemOperation itemOperation) {
        Optional<Item> oItem = itemRepository.findById(itemId);
        if (oItem.isPresent()){
            Item item = oItem.get();
            switch (itemOperation){
                case INCREASE -> busket.addItem(item);
                case DECREASE -> busket.removeItem(item);
                case REMOVE -> busket.removeAllItems(item);
                default -> throw new IllegalArgumentException();
            }
        }

    }
}
