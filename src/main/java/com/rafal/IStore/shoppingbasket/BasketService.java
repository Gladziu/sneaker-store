package com.rafal.IStore.shoppingbasket;

import com.rafal.IStore.shoppingbasket.BasketOperation;
import org.springframework.security.core.Authentication;

public interface BasketService {

    void itemOperation(Long itemId, int size, BasketOperation basketOperation, Authentication authentication);

}
