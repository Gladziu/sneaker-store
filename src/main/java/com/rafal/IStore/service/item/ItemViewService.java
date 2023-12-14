package com.rafal.IStore.service.item;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public interface ItemViewService {
    void populateUserInfoModel(Model model, Authentication authentication);

    void populateItemsModel(Model model, Authentication authentication);
}
