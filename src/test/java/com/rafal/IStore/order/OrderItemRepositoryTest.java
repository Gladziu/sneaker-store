package com.rafal.IStore.order;

import com.rafal.IStore.order.model.OrderHistory;
import com.rafal.IStore.order.model.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class OrderItemRepositoryTest {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    void saveAllAndFindAll_MultipleOrderItems_ReturnsOrderItemList() {
        // Given
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId(UUID.randomUUID());
        orderItem1.setQuantity(2);
        orderItem1.setPrice(BigDecimal.valueOf(100.00));

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setId(UUID.randomUUID());
        orderItem2.setQuantity(3);
        orderItem2.setPrice(BigDecimal.valueOf(150.00));

        // When
        orderItemRepository.saveAll(List.of(orderItem1, orderItem2));
        List<OrderItem> result = orderItemRepository.findAll();

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);

    }

    @Test
    void findById_NonExistingOrderItem() {
        //given
        UUID orderItemId = UUID.randomUUID();

        //when
        Optional<OrderItem> result = orderItemRepository.findById(orderItemId);

        //then
        assertThat(result).isEmpty();
    }
}