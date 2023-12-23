package com.rafal.IStore.order;

import com.rafal.IStore.order.model.OrderHistory;
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
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        OrderHistory order1 = OrderHistory.builder()
                .id(UUID.randomUUID())
                .email("bob@example.com")
                .build();

        OrderHistory order2 = OrderHistory.builder()
                .id(UUID.randomUUID())
                .email("bob@example.com")
                .build();

        OrderHistory order3 = OrderHistory.builder()
                .id(UUID.randomUUID())
                .email("james@example.com")
                .build();

        orderRepository.saveAll(List.of(order1, order2, order3));
    }

    @Test
    void findAllByEmail_ExistingEmail_ReturnsOrderHistoryList() {
        //given
        String email = "bob@example.com";

        //when
        List<OrderHistory> result = orderRepository.findAllByEmail(email);

        //then
        assertThat(result).hasSize(2);
    }

    @Test
    void findAllByEmail_NonExistingEmail() {
        //given
        String email = "carlos@example.com";

        //when
        List<OrderHistory> result = orderRepository.findAllByEmail(email);

        //then
        assertThat(result).hasSize(0);
    }
}