package com.rafal.sneakerstoreapp.shoppingbasket;

import com.rafal.sneakerstoreapp.shoppingbasket.model.BasketInfo;
import com.rafal.sneakerstoreapp.shoppingbasket.model.BasketItem;
import com.rafal.sneakerstoreapp.user.UserDto;
import com.rafal.sneakerstoreapp.user.UserRepository;
import com.rafal.sneakerstoreapp.user.UserService;
import com.rafal.sneakerstoreapp.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketInfoServiceTest {
    @Mock
    private BasketInfoRepository basketInfoRepository;
    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BasketRepository basketRepository;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private BasketInfoServiceImpl basketInfoService;
    private final UUID userId = UUID.randomUUID();
    private final BigDecimal itemPrice = BigDecimal.TEN;

    @Test
    void updateBasketInfo_ExistingBasketInfo() {
        //given
        BasketItem basketItem1 = BasketItem.builder()
                .price(BigDecimal.valueOf(100.99))
                .counter(1)
                .build();

        BasketItem basketItem2 = BasketItem.builder()
                .price(BigDecimal.valueOf(150.49))
                .counter(3)
                .build();
        BasketInfo basketInfo = new BasketInfo();
        when(basketRepository.findAllByUserId(userId)).thenReturn(List.of(basketItem1, basketItem2));
        when(basketInfoRepository.findByUserId(userId)).thenReturn(basketInfo);

        //when
        basketInfoService.updateBasketInfo(userId);

        //then
        verify(basketInfoRepository, times(1)).save(basketInfo);
    }

    @Test
    void updateBasketInfo_NonExistingBasketInfo() {
        //given
        BasketItem basketItem1 = BasketItem.builder()
                .price(BigDecimal.valueOf(100.99))
                .counter(1)
                .build();

        BasketItem basketItem2 = BasketItem.builder()
                .price(BigDecimal.valueOf(150.49))
                .counter(3)
                .build();

        when(basketRepository.findAllByUserId(userId)).thenReturn(List.of(basketItem1, basketItem2));
        when(basketInfoRepository.findByUserId(userId)).thenReturn(null);
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        //when
        basketInfoService.updateBasketInfo(userId);

        //then
        verify(basketInfoRepository, times(1)).save(any(BasketInfo.class));
    }

    @Test
    void updateBasketInfo_ExistingBasketInfo_EmptyBasket() {
        //given
        BasketInfo basketInfo = new BasketInfo();
        when(basketRepository.findAllByUserId(userId)).thenReturn(Collections.emptyList());
        when(basketInfoRepository.findByUserId(userId)).thenReturn(basketInfo);

        //when
        basketInfoService.updateBasketInfo(userId);

        //then
        verify(basketInfoRepository, times(1)).delete(basketInfo);
    }

    @Test
    void clearBasketInfo() {
        //given
        //when
        basketInfoService.clearBasketInfo(userId);

        //then
        verify(basketInfoRepository, times(1)).deleteByUserId(userId);
    }

    @Test
    void isBasketEmpty_BasketExists_ReturnsFalse() {
        // given
        UserDto currentUser = UserDto.builder()
                .id(userId)
                .build();

        BasketInfo basketInfo = new BasketInfo();
        basketInfo.setQuantity(1);
        basketInfo.setSum(BigDecimal.TEN);

        when(userService.getCurrentUser(authentication)).thenReturn(currentUser);
        when(basketInfoRepository.findByUserId(currentUser.getId())).thenReturn(basketInfo);

        // when
        boolean result = basketInfoService.isBasketEmpty(authentication);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void isBasketEmpty_BasketNotExists_ReturnsTrue() {
        // given
        UserDto currentUser = UserDto.builder()
                .id(userId)
                .build();

        when(userService.getCurrentUser(authentication)).thenReturn(currentUser);
        when(basketInfoRepository.findByUserId(any())).thenReturn(null);

        // when
        boolean result = basketInfoService.isBasketEmpty(authentication);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void getSum_BasketExists_ReturnsSum() {
        // given
        UserDto currentUser = UserDto.builder()
                .id(userId)
                .build();

        BasketInfo basketInfo = new BasketInfo();
        basketInfo.setSum(BigDecimal.TEN);

        when(userService.getCurrentUser(authentication)).thenReturn(currentUser);
        when(basketInfoRepository.findByUserId(currentUser.getId())).thenReturn(basketInfo);

        // when
        BigDecimal result = basketInfoService.getSum(authentication);

        // then
        assertThat(result).isEqualTo(BigDecimal.TEN);
    }

    @Test
    void getSum_WhenBasketDoesNotExist_ThenReturnZero() {
        // given
        UserDto currentUser = UserDto.builder()
                .id(userId)
                .build();

        when(userService.getCurrentUser(authentication)).thenReturn(currentUser);
        when(basketInfoRepository.findByUserId(any())).thenReturn(null);

        // when
        BigDecimal result = basketInfoService.getSum(authentication);

        // then
        assertThat(result).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void getQuantity_WhenBasketExists_ThenReturnQuantity() {
        // given
        UserDto currentUser = UserDto.builder()
                .id(userId)
                .build();

        BasketInfo basketInfo = new BasketInfo();
        basketInfo.setQuantity(2);

        when(userService.getCurrentUser(authentication)).thenReturn(currentUser);
        when(basketInfoRepository.findByUserId(currentUser.getId())).thenReturn(basketInfo);

        // when
        int result = basketInfoService.getQuantity(authentication);

        // then
        assertThat(result).isEqualTo(2);
    }

    @Test
    void getQuantity_WhenBasketDoesNotExist_ThenReturnZero() {
        // given
        UserDto currentUser = UserDto.builder()
                .id(userId)
                .build();

        when(userService.getCurrentUser(authentication)).thenReturn(currentUser);
        when(basketInfoRepository.findByUserId(any())).thenReturn(null);

        // when
        int result = basketInfoService.getQuantity(authentication);

        // then
        assertThat(result).isEqualTo(0);
    }
}