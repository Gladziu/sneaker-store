package com.rafal.sneakerstoreapp.shoppingbasket;

import com.rafal.sneakerstoreapp.item.model.Item;
import com.rafal.sneakerstoreapp.item.model.ItemWithSize;
import com.rafal.sneakerstoreapp.shoppingbasket.model.BasketItem;
import com.rafal.sneakerstoreapp.user.UserDto;
import com.rafal.sneakerstoreapp.user.UserRepository;
import com.rafal.sneakerstoreapp.user.UserService;
import com.rafal.sneakerstoreapp.user.model.User;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
class BasketItemServiceImpl implements BasketItemService {
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
        if (basketItem == null) {
            createBasketItem(itemWithSize, userId);
        } else if (isBasketItemQuantityValid(basketItem)) {
            updateBasket(basketItem, INCREASE_COUNTER_BY_ONE);
        }
    }

    private boolean isBasketItemQuantityValid(BasketItem basketItem) {
        return basketItem.getCounter() < MAX_QUANTITY_OF_ONE_TYPE;
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
    }

    @Override
    public void removeAllIdenticalItems(ItemWithSize itemWithSize, UUID userId) {
        BasketItem basketItem = getItemInBasket(userId, itemWithSize);
        if (basketItem != null) {
            basketRepository.delete(basketItem);
        }
    }

    @Transactional
    @Override
    public void clearBasket(UUID userId) {
        basketRepository.deleteAllByUserId(userId);
        basketInfoService.clearBasketInfo(userId);
    }

    @Override
    public void removeItemFromEachBasket(Item item) {
        List<BasketItem> basketItems = basketRepository.findAllByItem(item);
        if (!basketItems.isEmpty()) {
            for (BasketItem basketItem : basketItems) {
                UUID userId = basketItem.getUser().getId();
                basketRepository.delete(basketItem);
                basketInfoService.updateBasketInfo(userId);
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
        List<BasketItem> basketItems = basketRepository.findAllByUserIdAndItemAndSize(userId, itemWithSize.getItem(), itemWithSize.getSize());
        if (!basketItems.isEmpty()) {
            return basketItems.get(0);
        }
        return null;
    }

    private void createBasketItem(ItemWithSize itemWithSize, UUID userId) {
        int size = itemWithSize.getSize();
        Item item = itemWithSize.getItem();
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            BasketItem newItemInBasket = BasketItem.builder()
                    .size(size)
                    .counter(ONE_ITEM_IN_BASKET)
                    .price(item.getPrice())
                    .item(item)
                    .user(user.get())
                    .build();
            basketRepository.save(newItemInBasket);
        }
    }

}
