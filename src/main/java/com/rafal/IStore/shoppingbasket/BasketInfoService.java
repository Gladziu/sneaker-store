package com.rafal.IStore.shoppingbasket;

import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.UUID;

public interface BasketInfoService {
    void updateBasketInfo(UUID userId);

    void clearBasketInfo(UUID userId);

    boolean isBasketEmpty(Authentication authentication);

    BigDecimal getSum(Authentication authentication);

    int getQuantity(Authentication authentication);
}
