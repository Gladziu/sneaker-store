package com.rafal.IStore.service.basket;

import org.springframework.security.core.Authentication;

public interface BasketService {

    void itemOperation(Long itemId, int size, BasketOperation basketOperation, Authentication authentication);

}
