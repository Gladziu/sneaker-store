package com.rafal.IStore.item.controller;

import com.rafal.IStore.item.ItemViewService;
import com.rafal.IStore.shoppingbasket.BasketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
@ExtendWith(MockitoExtension.class)
class ItemControllerTest {
    @MockBean
    private BasketService basketService;
    @MockBean
    private ItemViewService itemViewService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user")
    void homePage_ReturnsHomeView() throws Exception {
        when(itemViewService.correctViewDueToRole(any(Model.class), any(Authentication.class)))
                .thenReturn("home");

        mockMvc.perform(get("/sneaker-store/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    @WithMockUser(username = "user")
    void addItemToBasket_ReturnsRedirectionToHome() throws Exception {
        Long itemId = 1L;
        int selectedSize = 42;

        mockMvc.perform(post("/sneaker-store/add/{itemId}", itemId)
                        .param("size", String.valueOf(selectedSize))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sneaker-store/home"));
    }
}