package com.rafal.IStore.service.order;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public interface OrderViewService {

    void populateSumQuantityAndBasketItems(Model model, Authentication authentication);

    void handleOrderValidationErrors(Model model, Authentication authentication);

    void handleEmptyBasket(Model model, Authentication authentication);

    void populateSummaryModel(Model model, Authentication authentication);

    void populateOrderHistoryModel(Model model, Authentication authentication);
}
