package com.rafal.sneakerstoreapp.order.controller;

import com.rafal.sneakerstoreapp.order.OrderDto;
import com.rafal.sneakerstoreapp.order.OrderViewService;
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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(OrderController.class)
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @MockBean
    private OrderViewService orderViewService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user")
    void summaryPage_ReturnsSummaryView() throws Exception {
        OrderDto orderDto = new OrderDto();

        when(orderViewService.summaryView(any(Model.class), any(Authentication.class))).thenReturn("summary");

        mockMvc.perform(get("/sneaker-store/order/summary")
                        .flashAttr("orderDto", orderDto))
                .andExpect(status().isOk())
                .andExpect(view().name("summary"));
    }

    @Test
    @WithMockUser(username = "user")
    void saveOrder_ReturnsThanksForShoppingView() throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.setFirstName("Bob");
        orderDto.setLastName("Smith");
        orderDto.setCity("Warsaw");
        orderDto.setAddress("Example 23");
        orderDto.setPostCode("11-111");

        BindingResult bindingResult = new BeanPropertyBindingResult(orderDto, "orderDto");

        when(orderViewService.processSavingOrder(eq(orderDto), eq(bindingResult), any(Model.class), any(Authentication.class)))
                .thenReturn("thanks-for-shopping");

        mockMvc.perform(post("/sneaker-store/order/save-order")
                        .flashAttr("orderDto", orderDto)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("thanks-for-shopping"));
    }

    @Test
    @WithMockUser(username = "user")
    void orderHistory_ReturnsOrderView() throws Exception {
        mockMvc.perform(get("/sneaker-store/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("orders"));
    }
}