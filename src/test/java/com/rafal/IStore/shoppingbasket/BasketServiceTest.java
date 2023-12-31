package com.rafal.IStore.shoppingbasket;

import com.rafal.IStore.item.ItemRepository;
import com.rafal.IStore.item.model.Item;
import com.rafal.IStore.item.model.ItemWithSize;
import com.rafal.IStore.user.UserDto;
import com.rafal.IStore.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private BasketItemService basketItemService;
    @Mock
    private UserService userService;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private BasketServiceImpl basketService;
    @Test
    void itemOperation_withValidSizeAndIncreaseOperation_callsBasketItemServiceAddItem() {
        //given
        Long itemId = 1L;
        int size = 42;
        BasketOperation basketOperation = BasketOperation.INCREASE;

        Item item = new Item();
        item.setId(itemId);

        UserDto currentUser = UserDto.builder().id(UUID.randomUUID()).build();
        when(userService.getCurrentUser(authentication)).thenReturn(currentUser);

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        //when
        basketService.itemOperation(itemId, size, basketOperation, authentication);

        //then
        verify(basketItemService, times(1)).addItem(any(ItemWithSize.class), eq(currentUser.getId()));
        verify(basketItemService, times(0)).removeItem(any(ItemWithSize.class), any(UUID.class));
        verify(basketItemService, times(0)).removeAllTheSameItems(any(ItemWithSize.class), any(UUID.class));
    }

    @Test
    void itemOperation_withValidSizeAndDecreaseOperation_callsBasketItemServiceRemoveItem() {
        //given
        Long itemId = 1L;
        int size = 42;
        BasketOperation basketOperation = BasketOperation.DECREASE;

        Item item = new Item();
        item.setId(itemId);

        UserDto currentUser = UserDto.builder().id(UUID.randomUUID()).build();
        when(userService.getCurrentUser(authentication)).thenReturn(currentUser);

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        //when
        basketService.itemOperation(itemId, size, basketOperation, authentication);

        //then
        verify(basketItemService, times(0)).addItem(any(ItemWithSize.class), any(UUID.class));
        verify(basketItemService, times(1)).removeItem(any(ItemWithSize.class), eq(currentUser.getId()));
        verify(basketItemService, times(0)).removeAllTheSameItems(any(ItemWithSize.class), any(UUID.class));
    }

    @Test
    void itemOperation_withValidSizeAndRemoveOperation_callsBasketItemServiceRemoveAllTheSameItems() {
        //given
        Long itemId = 1L;
        int size = 42;
        BasketOperation basketOperation = BasketOperation.REMOVE;

        Item item = new Item();
        item.setId(itemId);

        UserDto currentUser = UserDto.builder().id(UUID.randomUUID()).build();
        when(userService.getCurrentUser(authentication)).thenReturn(currentUser);

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        //when
        basketService.itemOperation(itemId, size, basketOperation, authentication);

        //then
        verify(basketItemService, times(0)).addItem(any(ItemWithSize.class), any(UUID.class));
        verify(basketItemService, times(0)).removeItem(any(ItemWithSize.class), any(UUID.class));
        verify(basketItemService, times(1)).removeAllTheSameItems(any(ItemWithSize.class), eq(currentUser.getId()));
    }

    @Test
    void itemOperation_withInvalidSize_doesNotCallBasketItemServiceMethods() {
        //given
        Long itemId = 1L;
        int invalidSize = 55;
        BasketOperation basketOperation = BasketOperation.INCREASE;

        //when
        basketService.itemOperation(itemId, invalidSize, basketOperation, authentication);

        //then
        verify(basketItemService, times(0)).addItem(any(ItemWithSize.class), any(UUID.class));
        verify(basketItemService, times(0)).removeItem(any(ItemWithSize.class), any(UUID.class));
        verify(basketItemService, times(0)).removeAllTheSameItems(any(ItemWithSize.class), any(UUID.class));
        verify(itemRepository, times(0)).findById(anyLong());
    }
}