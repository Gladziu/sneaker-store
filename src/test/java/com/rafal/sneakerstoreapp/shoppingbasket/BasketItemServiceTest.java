package com.rafal.sneakerstoreapp.shoppingbasket;

import com.rafal.sneakerstoreapp.item.model.Item;
import com.rafal.sneakerstoreapp.item.model.ItemWithSize;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketItemServiceTest {

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private BasketInfoService basketInfoService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private BasketItemServiceImpl basketItemService;
    private final UUID userId = UUID.randomUUID();

    @Test
    void addItem_AddNewItem() {
        //given
        int size = 42;
        ItemWithSize itemWithSize = new ItemWithSize(new Item(), size);

        when(basketRepository.findAllByUserIdAndItemAndSize(userId, itemWithSize.getItem(), itemWithSize.getSize()))
                .thenReturn(Collections.emptyList());
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        //when
        basketItemService.addItem(itemWithSize, userId);

        //then
        verify(basketRepository, times(1)).save(any(BasketItem.class));
    }

    @Test
    void addItem_AddExistingItem_ValidQuantity() {
        //given
        Item item = new Item();
        item.setId(1L);
        item.setName("Product A");
        item.setPrice(BigDecimal.valueOf(100));

        int size = 42;
        ItemWithSize itemWithSize = new ItemWithSize(item, size);

        BasketItem basketItem = BasketItem.builder()
                .id(UUID.randomUUID())
                .size(size)
                .counter(2)
                .item(item)
                .build();


        when(basketRepository.findAllByUserIdAndItemAndSize(userId, itemWithSize.getItem(), itemWithSize.getSize()))
                .thenReturn(List.of(basketItem));

        //when
        basketItemService.addItem(itemWithSize, userId);

        //then
        verify(basketRepository, times(1)).save(any(BasketItem.class));
    }

    @Test
    void addItem_AddExistingItem_QuantityLimitValue() {
        //given
        Item item = new Item();
        item.setId(1L);
        item.setName("Product A");
        item.setPrice(BigDecimal.valueOf(100));

        int size = 42;
        ItemWithSize itemWithSize = new ItemWithSize(item, size);

        BasketItem basketItem = BasketItem.builder()
                .id(UUID.randomUUID())
                .size(size)
                .counter(10)
                .item(item)
                .build();


        when(basketRepository.findAllByUserIdAndItemAndSize(userId, itemWithSize.getItem(), itemWithSize.getSize()))
                .thenReturn(List.of(basketItem));

        //when
        basketItemService.addItem(itemWithSize, userId);

        //then
        verify(basketRepository, never()).save(any(BasketItem.class));
    }

    @Test
    public void removeItem_ItemExistsInBasket() {
        //given
        Item item = new Item();
        item.setId(1L);
        item.setName("Product A");
        item.setPrice(BigDecimal.valueOf(100));

        int size = 42;
        ItemWithSize itemWithSize = new ItemWithSize(item, size);

        BasketItem basketItem = BasketItem.builder()
                .id(UUID.randomUUID())
                .size(size)
                .counter(2)
                .item(item)
                .build();

        when(basketRepository.findAllByUserIdAndItemAndSize(userId, itemWithSize.getItem(), itemWithSize.getSize()))
                .thenReturn(List.of(basketItem));

        //when
        basketItemService.removeItem(itemWithSize, userId);

        //then
        verify(basketRepository, times(1)).save(any(BasketItem.class));
    }

    @Test
    public void removeItem_LastItemInBasket() {
        //given
        int size = 42;
        ItemWithSize itemWithSize = new ItemWithSize(new Item(), size);
        BasketItem basketItem = new BasketItem();
        basketItem.setCounter(1);

        when(basketRepository.findAllByUserIdAndItemAndSize(userId, itemWithSize.getItem(), itemWithSize.getSize()))
                .thenReturn(List.of(basketItem));

        //when
        basketItemService.removeItem(itemWithSize, userId);

        //then
        verify(basketRepository, times(1)).delete(basketItem);
    }

    @Test
    void removeAllIdenticalItems_ItemExistsInBasket() {
        //given
        int size = 42;
        ItemWithSize itemWithSize = new ItemWithSize(new Item(), size);
        BasketItem basketItem = new BasketItem();
        basketItem.setCounter(1);

        when(basketRepository.findAllByUserIdAndItemAndSize(userId, itemWithSize.getItem(), itemWithSize.getSize())).
                thenReturn(List.of(basketItem));

        //when
        basketItemService.removeAllIdenticalItems(itemWithSize, userId);

        //then
        verify(basketRepository, times(1)).delete(basketItem);
    }

    @Test
    void clearBasket() {
        //given

        //when
        basketItemService.clearBasket(userId);

        //then
        verify(basketRepository).deleteAllByUserId(userId);
        verify(basketInfoService).clearBasketInfo(userId);
    }

    @Test
    void removeItemFromEachBasket_ItemsExistInBasket() {
        //given
        Item item = new Item();
        item.setId(1L);
        item.setPrice(BigDecimal.TEN);

        User user = new User();
        user.setId(userId);

        BasketItem basketItem1 = BasketItem.builder()
                .id(UUID.randomUUID())
                .size(42)
                .counter(2)
                .price(item.getPrice())
                .item(item)
                .user(user)
                .build();

        BasketItem basketItem2 = BasketItem.builder()
                .id(UUID.randomUUID())
                .size(42)
                .counter(1)
                .price(item.getPrice())
                .item(item)
                .user(user)
                .build();

        List<BasketItem> basketItems = List.of(basketItem1, basketItem2);

        when(basketRepository.findAllByItem(eq(item))).thenReturn(basketItems);

        //when
        basketItemService.removeItemFromEachBasket(item);

        //then
        verify(basketRepository, times(2)).delete(any(BasketItem.class));
    }

    @Test
    void removeItemFromEachBasket_NoItemsInBasket() {
        // given
        Item item = new Item();
        item.setId(1L);

        when(basketRepository.findAllByItem(eq(item))).thenReturn(Collections.emptyList());

        // when
        basketItemService.removeItemFromEachBasket(item);

        // then
        verify(basketRepository, never()).delete(any(BasketItem.class));
    }

    @Test
    void getBasketItem_ReturnsBasketItemList() {
        // given
        Authentication authentication = mock(Authentication.class);
        UserDto currentUser = UserDto.builder()
                .id(UUID.randomUUID())
                .email("bob@example.com")
                .build();

        BasketItem basketItem = new BasketItem();
        basketItem.setId(UUID.randomUUID());
        basketItem.setUser(new User());

        BasketItem basketItem2 = new BasketItem();
        basketItem2.setId(UUID.randomUUID());
        basketItem2.setUser(new User());

        List<BasketItem> basketItems = List.of(basketItem, basketItem2);

        // when
        when(userService.getCurrentUser(authentication)).thenReturn(currentUser);
        when(basketRepository.findAllByUserId(currentUser.getId())).thenReturn(basketItems);

        List<BasketItem> result = basketItemService.getBasketItems(authentication);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }

}