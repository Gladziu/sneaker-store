package com.rafal.IStore.shoppingbasket;

import com.rafal.IStore.shoppingbasket.model.BasketInfo;
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
class BasketInfoServiceImpl implements BasketInfoService {
    private static final int FIRST_ITEM_IN_BASKET = 1;
    private static final int ZERO_ITEMS_IN_BASKET = 0;
    private static final BigDecimal BASKET_VALUE_OF_ZERO = BigDecimal.ZERO;
    private final BasketInfoRepository basketInfoRepository;
    private final BasketRepository basketRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public BasketInfoServiceImpl(BasketInfoRepository basketInfoRepository, BasketRepository basketRepository, UserService userService, UserRepository userRepository) {
        this.basketInfoRepository = basketInfoRepository;
        this.basketRepository = basketRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void updateBasketInfo(UUID userId) {
        List<BasketItem> basketItems = basketRepository.findAllByUserId(userId);

        BigDecimal priceSum = basketItems.stream()
                .map(BasketItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int quantitySum = basketItems.stream()
                .mapToInt(BasketItem::getCounter)
                .sum();

        BasketInfo basketInfo = getBasketInfoByUserId(userId);
        if (basketInfo != null) {
            updateExistingBasketInfo(basketInfo, quantitySum, priceSum);
        } else {
            createNewBasketInfo(priceSum, userId);
        }
    }

    private void updateExistingBasketInfo(BasketInfo basketInfo, int quantitySum, BigDecimal priceSum) {
        if (quantitySum <= 0) {
            basketInfoRepository.delete(basketInfo);
        } else {
            basketInfo.setQuantity(quantitySum);
            basketInfo.setSum(priceSum);
            basketInfoRepository.save(basketInfo);
        }
    }

    private void createNewBasketInfo(BigDecimal price, UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            BasketInfo newItemInformation = new BasketInfo();
            newItemInformation.setUser(user.get());
            newItemInformation.setSum(price);
            newItemInformation.setQuantity(FIRST_ITEM_IN_BASKET);
            basketInfoRepository.save(newItemInformation);
        }
    }

    @Transactional
    @Override
    public void clearBasketInfo(UUID userId) {
        basketInfoRepository.deleteByUserId(userId);
    }

    @Override
    public boolean isBasketEmpty(Authentication authentication) {
        BasketInfo basketInfo = getBasketInfoByAuthentication(authentication);
        return basketInfo == null;
    }

    @Override
    public BigDecimal getSum(Authentication authentication) {
        BasketInfo basketInfo = getBasketInfoByAuthentication(authentication);
        if (basketInfo != null) {
            return basketInfo.getSum();
        }
        return BASKET_VALUE_OF_ZERO;
    }

    @Override
    public int getQuantity(Authentication authentication) {
        BasketInfo basketInfo = getBasketInfoByAuthentication(authentication);
        if (basketInfo != null) {
            return basketInfo.getQuantity();
        }
        return ZERO_ITEMS_IN_BASKET;
    }

    private BasketInfo getBasketInfoByAuthentication(Authentication authentication) {
        UserDto currentUser = userService.getCurrentUser(authentication);
        return basketInfoRepository.findByUserId(currentUser.getId());
    }

    private BasketInfo getBasketInfoByUserId(UUID userId) {
        return basketInfoRepository.findByUserId(userId);
    }
}
