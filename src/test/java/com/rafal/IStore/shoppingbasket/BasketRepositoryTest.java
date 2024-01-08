package com.rafal.IStore.shoppingbasket;

import com.rafal.IStore.item.ItemRepository;
import com.rafal.IStore.item.model.Item;
import com.rafal.IStore.shoppingbasket.model.BasketItem;
import com.rafal.IStore.user.UserRepository;
import com.rafal.IStore.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class BasketRepositoryTest {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;
    private UUID userId;
    private User user;
    private Item item;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setEmail("bob@example.com");
        userRepository.save(user);

        item = new Item();
        item.setName("Sample Item");
        itemRepository.save(item);

        int size = 42;

        BasketItem basketItem = BasketItem.builder()
                .id(UUID.randomUUID())
                .size(size)
                .item(item)
                .user(user)
                .build();
        basketRepository.save(basketItem);

        userId = basketItem.getUser().getId();
    }

    @Test
    void findAllByUserIdAndItemAndSize_ExistingBasketItem_ReturnsBasketItems() {
        //given
        String itemName = "Sample Item";
        String email = "bob@example.com";
        int size = 42;

        //when
        List<BasketItem> result = basketRepository.findAllByUserIdAndItemAndSize(userId, item, size);

        //then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getSize()).isEqualTo(size);
        assertThat(result.get(0).getUser().getEmail()).isEqualTo(email);
        assertThat(result.get(0).getItem().getName()).isEqualTo(itemName);
    }

    @Test
    void findAllByUserIdAndItemAndSize_NonExistingBasketItems() {
        //given
        Item item2 = new Item();
        item2.setName("Sample Item 2");
        itemRepository.save(item2);
        int size = 42;

        //when
        List<BasketItem> result = basketRepository.findAllByUserIdAndItemAndSize(userId, item2, size);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void findAllByItem_ExistingBasketItem_ReturnsBasketItemList() {
        //given
        int size2 = 39;

        BasketItem basketItem = new BasketItem();
        basketItem.setSize(size2);
        basketItem.setUser(user);
        basketItem.setItem(item);
        basketRepository.save(basketItem);

        //when
        List<BasketItem> result = basketRepository.findAllByItem(item);

        //then
        assertThat(result).hasSize(2);
        assertThat(result).isNotEmpty();
    }

    @Test
    void findAllByItem_NonExistingBasketItem() {
        //given
        Item item2 = new Item();
        item2.setName("Sample Item B");
        itemRepository.save(item2);
        //when
        List<BasketItem> result = basketRepository.findAllByItem(item2);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void findAllByUserId_ExistingBasketItem_ReturnsBasketItemList() {
        //given
        User user2 = new User();
        user2.setEmail("james@example.com");
        userRepository.save(user2);

        Item item2 = new Item();
        item2.setName("Sample Item 2");
        itemRepository.save(item2);

        int size2 = 39;

        BasketItem basketItem2 = BasketItem.builder()
                .id(UUID.randomUUID())
                .size(size2)
                .item(item2)
                .user(user2)
                .build();
        basketRepository.save(basketItem2);

        //then
        List<BasketItem> result = basketRepository.findAllByUserId(userId);

        //then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUser().getEmail()).isEqualTo("bob@example.com");
    }

    @Test
    void findAllByUserId_NonExistingBasketItem() {
        //given
        UUID userId2 = UUID.randomUUID();

        //when
        List<BasketItem> result = basketRepository.findAllByUserId(userId2);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void deleteAllByUserId() {
        //given
        //when
        basketRepository.deleteAllByUserId(userId);

        //then
        List<BasketItem> result = basketRepository.findAllByUserId(userId);
        assertThat(result).isEmpty();
    }


}