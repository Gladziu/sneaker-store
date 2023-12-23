package com.rafal.IStore.item;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public interface ItemViewService {
    void populateItemsModel(Model model, Authentication authentication);

    String correctViewDueToRole(Model model, Authentication authentication);
}
