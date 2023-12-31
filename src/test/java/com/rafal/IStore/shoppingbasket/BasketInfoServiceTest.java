package com.rafal.IStore.shoppingbasket;

import com.rafal.IStore.shoppingbasket.model.BasketInfo;
import com.rafal.IStore.user.UserDto;
import com.rafal.IStore.user.UserRepository;
import com.rafal.IStore.user.UserService;
import com.rafal.IStore.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
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
    private Authentication authentication;

    @InjectMocks
    private BasketInfoServiceImpl basketInfoService;
    private final UUID userId = UUID.randomUUID();
    private final BigDecimal itemPrice = BigDecimal.TEN;

    @Test
    void addItemDetails_BasketExists_UpdateBasketInfo() {
        //given
        BasketInfo basketInfo = new BasketInfo();
        basketInfo.setQuantity(1);
        basketInfo.setSum(BigDecimal.TEN);

        when(basketInfoRepository.findByUserId(userId)).thenReturn(basketInfo);

        //when
        basketInfoService.addItemDetails(itemPrice, userId);

        //then
        verify(basketInfoRepository, times(1)).save(basketInfo);
    }

    @Test
    void addItemDetails_BasketNotExists_CreateNewBasketInfo() {
        //given
        when(basketInfoRepository.findByUserId(userId)).thenReturn(null);
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        //when
        basketInfoService.addItemDetails(itemPrice, userId);

        //then
        verify(basketInfoRepository, times(1)).save(any());
    }

    @Test
    void removeItemDetails_BasketExists_UpdateBasketInfo() {
        //given
        BasketInfo basketInfo = new BasketInfo();
        basketInfo.setQuantity(2);
        basketInfo.setSum(new BigDecimal("20"));

        when(basketInfoRepository.findByUserId(userId)).thenReturn(basketInfo);

        //when
        basketInfoService.removeItemDetails(itemPrice, userId);

        //then
        verify(basketInfoRepository, times(1)).save(basketInfo);
    }

    @Test
    void removeItemDetails_BasketNotExists() {
        //given
        when(basketInfoRepository.findByUserId(userId)).thenReturn(null);

        //when
        basketInfoService.removeItemDetails(itemPrice, userId);

        //then
        verify(basketInfoRepository, never()).save(any());
    }


    @Test
    void removeAllSameItemsDetails_BasketExists_UpdateBasketInfo() {
        //given
        int initialQuantity = 3;
        BasketInfo basketInfo = new BasketInfo();
        basketInfo.setQuantity(initialQuantity);
        basketInfo.setSum(new BigDecimal("30"));

        when(basketInfoRepository.findByUserId(userId)).thenReturn(basketInfo);

        //when
        int quantityToRemove = 2;
        basketInfoService.removeAllSameItemsDetails(quantityToRemove, itemPrice, userId);

        //then
        verify(basketInfoRepository, times(1)).save(basketInfo);
    }

    @Test
    void removeAllSameItemsDetails_BasketNotExists() {
        //given
        when(basketInfoRepository.findByUserId(userId)).thenReturn(null);

        //when
        basketInfoService.removeAllSameItemsDetails(2, itemPrice, userId);

        //then
        verify(basketInfoRepository, never()).save(any());
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