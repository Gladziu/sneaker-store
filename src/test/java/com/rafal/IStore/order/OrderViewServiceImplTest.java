package com.rafal.IStore.order;

import com.rafal.IStore.shoppingbasket.BasketInfoService;
import com.rafal.IStore.shoppingbasket.BasketItemService;
import com.rafal.IStore.user.UserDto;
import com.rafal.IStore.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderViewServiceImplTest {

    @Mock
    private OrderService orderService;

    @Mock
    private UserService userService;

    @Mock
    private BasketItemService basketItemService;

    @Mock
    private BasketInfoService basketInfoService;

    @Mock
    private OrderDto orderDto;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private OrderViewServiceImpl orderViewService;

    @Test
    void testProcessSavingOrder_BindingErrorsTrue() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        String result = orderViewService.processSavingOrder(orderDto, bindingResult, model, authentication);

        // Then
        assertEquals("summary", result);
        verify(orderService, never()).saveOrder(orderDto, authentication);
    }


    @Test
    void processSavingOrder_BindingErrorsFalse() {
        // Given
        OrderDto orderDto = new OrderDto();

        when(bindingResult.hasErrors()).thenReturn(false);

        // When
        String result = orderViewService.processSavingOrder(orderDto, bindingResult, model, authentication);

        // Then
        assertThat(result).isEqualTo("thanks-for-shopping");
        verify(orderService, times(1)).saveOrder(orderDto, authentication);
    }

    @Test
    void summaryView_EmptyBasket() {
        // Given
        Model model = mock(Model.class);
        Authentication authentication = mock(Authentication.class);

        when(basketInfoService.isBasketEmpty(authentication)).thenReturn(true);

        // When
        String result = orderViewService.summaryView(model, authentication);

        // Then
        assertThat(result).isEqualTo("basket");
    }

    @Test
    void summaryView_NonEmptyBasket() {
        // Given
        UserDto currentUser = UserDto.builder()
                .id(UUID.randomUUID())
                .build();

        when(basketInfoService.isBasketEmpty(authentication)).thenReturn(false);
        when(userService.getCurrentUser(authentication)).thenReturn(currentUser);
        // When
        String result = orderViewService.summaryView(model, authentication);

        // Then
        assertThat(result).isEqualTo("summary");
    }
}