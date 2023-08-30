package com.rafal.IStore.service.basket;

import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.model.item.ItemOperation;
import com.rafal.IStore.model.item.ItemWithSize;
import com.rafal.IStore.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final ItemRepository itemRepository;
    private final Basket basket;

    @Override
    public List<Item> getAllItems(){ return itemRepository.findAll(); }

    @Override
    public boolean isBasketEmpty(){ return basket.isBasketQuantityZero(); }

    @Override
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
