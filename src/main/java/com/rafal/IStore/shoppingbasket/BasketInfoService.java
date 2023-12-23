package com.rafal.IStore.shoppingbasket;

import com.rafal.IStore.user.UserDto;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.UUID;

public interface BasketInfoService {
    void addItemDetails(BigDecimal price, UUID userId);

    void removeItemDetails(BigDecimal price, UUID userId);

    void removeAllSameItemsDetails(int quantity, BigDecimal price, UUID userId);

    void clearBasketInfo(UUID userId);

    boolean isBasketEmpty(Authentication authentication);

    BigDecimal getSum(Authentication authentication);

    int getQuantity(Authentication authentication);
}
