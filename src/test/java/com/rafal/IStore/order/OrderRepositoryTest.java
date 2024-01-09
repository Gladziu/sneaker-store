package com.rafal.IStore.order;

import com.rafal.IStore.order.model.OrderHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String date1 = "2015-10-10";
        LocalDateTime dateTime1 = LocalDate.parse(date1, formatter).atStartOfDay();
        OrderHistory order1 = OrderHistory.builder()
                .id(UUID.randomUUID())
                .email("bob@example.com")
                .postCode("11-111")
                .created(dateTime1)
                .build();

        String date2 = "2023-12-15";
        LocalDateTime dateTime2 = LocalDate.parse(date2, formatter).atStartOfDay();
        OrderHistory order2 = OrderHistory.builder()
                .id(UUID.randomUUID())
                .created(dateTime2)
                .email("bob@example.com")
                .postCode("22-222")
                .build();

        OrderHistory order3 = OrderHistory.builder()
                .id(UUID.randomUUID())
                .email("james@example.com")
                .postCode("33-333")
                .build();

        orderRepository.saveAll(List.of(order1, order2, order3));
    }

    @Test
    void findAllByEmailOrderByCreatedDesc_ExistingEmail_ReturnsOrderHistoryList() {
        //given
        String email = "bob@example.com";

        //when
        List<OrderHistory> result = orderRepository.findAllByEmailOrderByCreatedDesc(email);

        //then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getPostCode()).isEqualTo("22-222");
    }

    @Test
    void findAllByEmailOrderByCreatedDesc_NonExistingEmail() {
        //given
        String email = "carlos@example.com";

        //when
        List<OrderHistory> result = orderRepository.findAllByEmailOrderByCreatedDesc(email);

        //then
        assertThat(result).hasSize(0);
    }
}