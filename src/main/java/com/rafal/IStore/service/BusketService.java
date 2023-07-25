package com.rafal.IStore.service;

import com.rafal.IStore.Busket;
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

    public void addItemToBusket(Long itemId){
        Optional<Item> oItem = itemRepository.findById(itemId);
        if (oItem.isPresent()){
            Item item = oItem.get();
            busket.addItem(item);
        }

    }

    public void removeItemFromBusket(Long itemId) {
        Optional<Item> oItem = itemRepository.findById(itemId);
        if (oItem.isPresent()){
            Item item = oItem.get();
            busket.removeItem(item);
        }
    }
}
