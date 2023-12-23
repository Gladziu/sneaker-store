package com.rafal.IStore.order;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface OrderViewService {

    void populateSumQuantityAndBasketItems(Model model, Authentication authentication);

    void populateOrderHistoryModel(Model model, Authentication authentication);

    String summaryView(Model model, Authentication authentication);

    String processSavingOrder(OrderDto orderDto, BindingResult bindingResult, Model model, Authentication authentication);
}
