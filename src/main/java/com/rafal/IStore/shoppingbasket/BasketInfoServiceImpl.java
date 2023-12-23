package com.rafal.IStore.shoppingbasket;

import com.rafal.IStore.shoppingbasket.model.BasketInfo;
import com.rafal.IStore.user.UserDto;
import com.rafal.IStore.user.UserRepository;
import com.rafal.IStore.user.UserService;
import com.rafal.IStore.user.model.User;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
class BasketInfoServiceImpl implements BasketInfoService {
    private static final int INCREASE_QUANTITY_BY_ONE = 1;
    private static final int DECREASE_QUANTITY_BY_ONE = -1;
    private static final int ZERO_ITEMS_IN_BASKET = 0;
    private static final BigDecimal BASKET_VALUE_OF_ZERO = BigDecimal.ZERO;
    private final BasketInfoRepository basketInfoRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public BasketInfoServiceImpl(BasketInfoRepository basketInfoRepository, UserService userService, UserRepository userRepository) {
        this.basketInfoRepository = basketInfoRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void addItemDetails(BigDecimal price, UUID userId) {
        BasketInfo basketInfo = getBasketInfoByUserId(userId);
        if (basketInfo != null) {
            updateBasketInfo(basketInfo, price, INCREASE_QUANTITY_BY_ONE);
        } else {
            createNewBasketInfo(price, userId);
        }
    }

    @Override
    public void removeItemDetails(BigDecimal price, UUID userId) {
        BasketInfo basketInfo = getBasketInfoByUserId(userId);
        if (basketInfo != null) {
            updateBasketInfo(basketInfo, price, DECREASE_QUANTITY_BY_ONE);
        }
    }

    @Override
    public void removeAllSameItemsDetails(int quantity, BigDecimal price, UUID userId) {
        BasketInfo basketInfo = getBasketInfoByUserId(userId);
        if (basketInfo != null) {
            updateBasketInfo(basketInfo, price, -quantity);
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

    private void updateBasketInfo(BasketInfo basketInfo, BigDecimal price, int quantityChange) {
        int updatedQuantity = basketInfo.getQuantity() + quantityChange;
        if (updatedQuantity > 0) {
            basketInfo.setQuantity(updatedQuantity);
            BigDecimal sumOfItem = price.multiply(BigDecimal.valueOf(quantityChange));
            basketInfo.setSum(basketInfo.getSum().add(sumOfItem));
            basketInfoRepository.save(basketInfo);
        } else {
            basketInfoRepository.delete(basketInfo);
        }
    }

    private void createNewBasketInfo(BigDecimal price, UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            BasketInfo newItemInformation = new BasketInfo();
            newItemInformation.setUser(user.get());
            newItemInformation.setSum(price);
            newItemInformation.setQuantity(1);
            basketInfoRepository.save(newItemInformation);
        }

    }
}
