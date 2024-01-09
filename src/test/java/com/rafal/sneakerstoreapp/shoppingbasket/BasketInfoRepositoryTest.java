package com.rafal.sneakerstoreapp.shoppingbasket;

import com.rafal.sneakerstoreapp.shoppingbasket.model.BasketInfo;
import com.rafal.sneakerstoreapp.user.UserRepository;
import com.rafal.sneakerstoreapp.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class BasketInfoRepositoryTest {

    @Autowired
    private BasketInfoRepository basketInfoRepository;
    @Autowired
    private UserRepository userRepository;
    private UUID userId;

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setEmail("bob@example.com");
        userRepository.save(user);

        BasketInfo basketInfo = new BasketInfo();
        basketInfo.setUser(user);
        basketInfo.setSum(BigDecimal.valueOf(100));
        basketInfo.setQuantity(2);
        basketInfoRepository.save(basketInfo);

        userId = basketInfo.getUser().getId();

    }

    @Test
    void findByUserId_ExistingBasketInfo_ReturnsBasketInfo() {
        //given
        int quantity = 2;
        //when
        BasketInfo result = basketInfoRepository.findByUserId(userId);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getQuantity()).isEqualTo(quantity);
    }

    @Test
    void findByUserId_NonExistingBasketInfo() {
        //given
        UUID userId2 = UUID.randomUUID();

        //when
        BasketInfo result = basketInfoRepository.findByUserId(userId2);

        //then
        assertThat(result).isNull();
    }

    @Test
    void deleteByUserId() {
        //given
        //when
        basketInfoRepository.deleteByUserId(userId);

        //then
        BasketInfo result = basketInfoRepository.findByUserId(userId);
        assertThat(result).isNull();
    }
}