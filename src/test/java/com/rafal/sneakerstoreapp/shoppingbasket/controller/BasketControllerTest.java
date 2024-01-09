package com.rafal.sneakerstoreapp.shoppingbasket.controller;

import com.rafal.sneakerstoreapp.order.OrderViewService;
import com.rafal.sneakerstoreapp.shoppingbasket.BasketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BasketController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class BasketControllerTest {

    @MockBean
    private BasketService basketService;

    @MockBean
    private Authentication authentication;

    @MockBean
    private OrderViewService orderViewService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void basketPage_ReturnsBasketView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sneaker-store/order/basket"))
                .andExpect(status().isOk())
                .andExpect(view().name("basket"));
    }

    @Test
    void increaseItem_ReturnsRedirectionToBasketPage() throws Exception {
        Long itemId = 1L;
        int size = 42;

        mockMvc.perform(MockMvcRequestBuilders.get("/sneaker-store/order/increase/{itemId}/{size}", itemId, size))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sneaker-store/order/basket"));
    }

    @Test
    void decreaseItem_ReturnsRedirectionToBasketPage() throws Exception {
        Long itemId = 1L;
        int size = 42;

        mockMvc.perform(MockMvcRequestBuilders.get("/sneaker-store/order/decrease/{itemId}/{size}", itemId, size))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sneaker-store/order/basket"));
    }

    @Test
    void removeItemFromBasket_ReturnsRedirectionToBasketPage() throws Exception {
        Long itemId = 1L;
        int size = 42;

        mockMvc.perform(get("/sneaker-store/order/remove/{itemId}/{size}", itemId, size))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sneaker-store/order/basket"));

    }
}