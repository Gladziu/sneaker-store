package com.rafal.IStore.service.basket;

import com.rafal.IStore.model.basket.BasketItem;
import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.model.item.ItemWithSize;
import com.rafal.IStore.model.user.User;
import com.rafal.IStore.repository.basket.BasketRepository;
import com.rafal.IStore.service.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BasketItemServiceImpl implements BasketItemService {
    private static final int DEFAULT_ITEM_COUNT = 1;
    private static final int INCREASE_COUNTER_BY_ONE = 1;
    private static final int DECREASE_COUNTER_BY_ONE = -1;
    private final BasketRepository basketRepository;
    private final BasketInfoService basketInfoService;
    private final UserService userService;

    public BasketItemServiceImpl(BasketRepository basketRepository, BasketInfoService basketInfoService, UserService userService) {
        this.basketRepository = basketRepository;
        this.basketInfoService = basketInfoService;
        this.userService = userService;
    }

    @Override
    public void addItem(ItemWithSize itemWithSize, User user) {
        BasketItem itemInBasketItem = getItemInBasket(user, itemWithSize);
        if (itemInBasketItem != null) {
            updateBasket(itemInBasketItem, INCREASE_COUNTER_BY_ONE);
        } else {
            BasketItem newItem = createBasketItem(itemWithSize, user);
            basketRepository.save(newItem);
        }
        BigDecimal price = itemWithSize.getItem().getPrice();
        basketInfoService.appendOneItem(price, user);
    }

    @Override
    public void removeItem(ItemWithSize itemWithSize, User user) {
        BasketItem itemInBasketItem = getItemInBasket(user, itemWithSize);
        if (itemInBasketItem != null) {
            if (itemInBasketItem.getCounter() > 1) {
                updateBasket(itemInBasketItem, DECREASE_COUNTER_BY_ONE);
            } else {
                basketRepository.delete(itemInBasketItem);
            }
        }
        BigDecimal price = itemWithSize.getItem().getPrice();
        basketInfoService.removeOneItem(price, user);
    }

    @Override
    public void removeAllItems(ItemWithSize itemWithSize, User user) {
        BasketItem itemInBasketItem = getItemInBasket(user, itemWithSize);
        if (itemInBasketItem != null) {
            int counter = itemInBasketItem.getCounter();
            basketRepository.delete(itemInBasketItem);
            BigDecimal price = itemWithSize.getItem().getPrice();
            basketInfoService.removeAllItems(counter, price, user);
        }
    }

    @Transactional
    @Override
    public void clearBasket(User user) {
        basketInfoService.clearBasketInfo(user);
        basketRepository.deleteAllByUser(user);
    }

    @Override
    public void deleteBasketItems(Item item) {
        List<BasketItem> basketItemItems = basketRepository.findAllByItem(item);
        if (!basketItemItems.isEmpty()) {
            for (BasketItem basketItem : basketItemItems) {
                basketInfoService.removeAllItems(basketItem.getCounter(), basketItem.getItem().getPrice(), basketItem.getUser());
                basketRepository.delete(basketItem);
            }
        }
    }

    @Override
    public List<BasketItem> getBasketItems(Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        return basketRepository.findAllByUser(user);
    }

    private void updateBasket(BasketItem itemInBasketItem, int counterChange) {
        int updatedCounter = itemInBasketItem.getCounter() + counterChange;
        BigDecimal price = itemInBasketItem.getItem().getPrice();
        BigDecimal updatedPrice = price.multiply(BigDecimal.valueOf(updatedCounter));
        itemInBasketItem.setCounter(updatedCounter);
        itemInBasketItem.setPrice(updatedPrice);
        basketRepository.save(itemInBasketItem);
    }

    private BasketItem getItemInBasket(User user, ItemWithSize itemWithSize) {
        return basketRepository.findByUserAndItemAndSize(user, itemWithSize.getItem(), itemWithSize.getSize());
    }

    private BasketItem createBasketItem(ItemWithSize itemWithSize, User user) {
        int size = itemWithSize.getSize();
        Item item = itemWithSize.getItem();
        return BasketItem.builder()
                .size(size)
                .counter(DEFAULT_ITEM_COUNT)
                .price(item.getPrice())
                .item(item)
                .user(user)
                .build();
    }

}
