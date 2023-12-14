package com.rafal.IStore.service.basket;

import com.rafal.IStore.model.basket.BasketInfo;
import com.rafal.IStore.model.user.User;
import com.rafal.IStore.repository.basket.BasketInfoRepository;
import com.rafal.IStore.service.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BasketInfoServiceImpl implements BasketInfoService {
    private static final int INCREASE_QUANTITY_BY_ONE = 1;
    private static final int DECREASE_QUANTITY_BY_ONE = -1;
    private static final int ZERO_ITEMS_IN_BASKET = 0;
    private final BasketInfoRepository basketInfoRepository;
    private final UserService userService;

    public BasketInfoServiceImpl(BasketInfoRepository basketInfoRepository, UserService userService) {
        this.basketInfoRepository = basketInfoRepository;
        this.userService = userService;
    }

    @Override
    public void appendOneItem(BigDecimal price, User user) {
        BasketInfo basketInfo = getBasketInfoByUser(user);
        if (basketInfo != null) {
            updateBasketInfo(basketInfo, price, INCREASE_QUANTITY_BY_ONE);
        } else {
            createNewBasketInfo(price, user);
        }
    }

    @Override
    public void removeOneItem(BigDecimal price, User user) {
        BasketInfo basketInfo = getBasketInfoByUser(user);
        if (basketInfo != null) {
            updateBasketInfo(basketInfo, price, DECREASE_QUANTITY_BY_ONE);
        }
    }

    @Override
    public void removeAllItems(int quantity, BigDecimal price, User user) {
        BasketInfo basketInfo = getBasketInfoByUser(user);
        if (basketInfo != null) {
            updateBasketInfo(basketInfo, price, -quantity);
        }
    }

    @Transactional
    @Override
    public void clearBasketInfo(User user) {
        basketInfoRepository.deleteByUser(user);
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
        return BigDecimal.ZERO;
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
        User user = userService.getCurrentUser(authentication);
        return basketInfoRepository.findByUser(user);
    }

    private BasketInfo getBasketInfoByUser(User user) {
        return basketInfoRepository.findByUser(user);
    }

    private void updateBasketInfo(BasketInfo basketInfo, BigDecimal price, int quantityChange) {
        int updatedQuantity = basketInfo.getQuantity() + quantityChange;
        if (updatedQuantity > 0) {
            basketInfo.setQuantity(updatedQuantity);
            BigDecimal sumOfItem = price.multiply(BigDecimal.valueOf(quantityChange));
            basketInfo.setSum(basketInfo.getSum().add(sumOfItem));
            basketInfoRepository.save(basketInfo);
        } else {
            basketInfoRepository.deleteById(basketInfo.getId());
        }
    }

    private void createNewBasketInfo(BigDecimal price, User user) {
        BasketInfo newItemInformation = new BasketInfo();
        newItemInformation.setUser(user);
        newItemInformation.setSum(price);
        newItemInformation.setQuantity(1);
        basketInfoRepository.save(newItemInformation);
    }
}
