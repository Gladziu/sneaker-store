package com.rafal.IStore.service.basket;

import com.rafal.IStore.model.user.User;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;

public interface BasketInfoService {
    void appendOneItem(BigDecimal price, User user);

    void removeOneItem(BigDecimal price, User user);

    void removeAllItems(int quantity, BigDecimal price, User user);

    void clearBasketInfo(User user);

    boolean isBasketEmpty(Authentication authentication);

    BigDecimal getSum(Authentication authentication);

    int getQuantity(Authentication authentication);
}
