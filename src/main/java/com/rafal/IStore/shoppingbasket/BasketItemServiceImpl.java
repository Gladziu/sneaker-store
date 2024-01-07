package com.rafal.IStore.shoppingbasket;

import com.rafal.IStore.item.model.Item;
import com.rafal.IStore.item.model.ItemWithSize;
import com.rafal.IStore.shoppingbasket.model.BasketItem;
import com.rafal.IStore.user.UserDto;
import com.rafal.IStore.user.UserRepository;
import com.rafal.IStore.user.UserService;
import com.rafal.IStore.user.model.User;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
class BasketItemServiceImpl implements BasketItemService {
    private static final int DEFAULT_ITEM_COUNT = 1;
    private static final int ONE_ITEM_IN_BASKET = 1;
    private static final int INCREASE_COUNTER_BY_ONE = 1;
    private static final int DECREASE_COUNTER_BY_ONE = -1;
    private static final int MAX_QUANTITY_OF_ONE_TYPE = 10;
    private final BasketRepository basketRepository;
    private final BasketInfoService basketInfoService;
    private final UserService userService;
    private final UserRepository userRepository;

    public BasketItemServiceImpl(BasketRepository basketRepository, BasketInfoService basketInfoService, UserService userService, UserRepository userRepository) {
        this.basketRepository = basketRepository;
        this.basketInfoService = basketInfoService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void addItem(ItemWithSize itemWithSize, UUID userId) {
        BasketItem basketItem = getItemInBasket(userId, itemWithSize);
        if (basketItem != null) {
            if (!isBasketItemQuantityValid(basketItem)) {
                return;
            }
            updateBasket(basketItem, INCREASE_COUNTER_BY_ONE);
        } else {
            BasketItem newItemInBasket = createBasketItem(itemWithSize, userId);
            basketRepository.save(newItemInBasket);
        }
        addItemToBasketInfo(itemWithSize, userId);
    }

    private boolean isBasketItemQuantityValid(BasketItem basketItem) {
        return basketItem.getCounter() < MAX_QUANTITY_OF_ONE_TYPE;
    }

    private void addItemToBasketInfo(ItemWithSize itemWithSize, UUID userId) {
        BigDecimal price = itemWithSize.getItem().getPrice();
        basketInfoService.addItemDetails(price, userId);
    }

    @Override
    public void removeItem(ItemWithSize itemWithSize, UUID userId) {
        BasketItem basketItem = getItemInBasket(userId, itemWithSize);
        if (basketItem != null) {
            int numberOfItemsInBasket = basketItem.getCounter();
            if (numberOfItemsInBasket > ONE_ITEM_IN_BASKET) {
                updateBasket(basketItem, DECREASE_COUNTER_BY_ONE);
            } else {
                basketRepository.delete(basketItem);
            }
        }
        removeItemFromBasketInfo(itemWithSize, userId);
    }

    private void removeItemFromBasketInfo(ItemWithSize itemWithSize, UUID userId) {
        BigDecimal price = itemWithSize.getItem().getPrice();
        basketInfoService.removeItemDetails(price, userId);
    }

    @Override
    public void removeAllTheSameItems(ItemWithSize itemWithSize, UUID userId) {
        BasketItem basketItem = getItemInBasket(userId, itemWithSize);
        if (basketItem != null) {
            int counter = basketItem.getCounter();
            basketRepository.delete(basketItem);
            removeAllSameItemsFromBasketInfo(itemWithSize, userId, counter);
        }
    }

    private void removeAllSameItemsFromBasketInfo(ItemWithSize itemWithSize, UUID userId, int counter) {
        BigDecimal price = itemWithSize.getItem().getPrice();
        basketInfoService.removeAllSameItemsDetails(counter, price, userId);
    }

    @Transactional
    @Override
    public void clearBasket(UUID userId) {
        basketInfoService.clearBasketInfo(userId);
        basketRepository.deleteAllByUserId(userId);
    }

    @Override
    public void deleteBasketItems(Item item) {
        List<BasketItem> basketItems = basketRepository.findAllByItem(item);
        if (!basketItems.isEmpty()) {
            for (BasketItem basketItem : basketItems) {
                basketInfoService.removeAllSameItemsDetails(basketItem.getCounter(), basketItem.getItem().getPrice(), basketItem.getUser().getId());
                basketRepository.delete(basketItem);
            }
        }
    }

    @Override
    public List<BasketItem> getBasketItems(Authentication authentication) {
        UserDto currentUser = userService.getCurrentUser(authentication);
        return basketRepository.findAllByUserId(currentUser.getId());
    }

    private void updateBasket(BasketItem basketItem, int counterChange) {
        int updatedCounter = basketItem.getCounter() + counterChange;
        BigDecimal price = basketItem.getItem().getPrice();
        BigDecimal updatedPrice = price.multiply(BigDecimal.valueOf(updatedCounter));
        basketItem.setCounter(updatedCounter);
        basketItem.setPrice(updatedPrice);
        basketRepository.save(basketItem);
    }

    private BasketItem getItemInBasket(UUID userId, ItemWithSize itemWithSize) {
        return basketRepository.findByUserIdAndItemAndSize(userId, itemWithSize.getItem(), itemWithSize.getSize());
    }

    private BasketItem createBasketItem(ItemWithSize itemWithSize, UUID userId) {
        int size = itemWithSize.getSize();
        Item item = itemWithSize.getItem();
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return BasketItem.builder()
                    .size(size)
                    .counter(DEFAULT_ITEM_COUNT)
                    .price(item.getPrice())
                    .item(item)
                    .user(user.get())
                    .build();
        } else {
            return new BasketItem();
        }

    }

}
