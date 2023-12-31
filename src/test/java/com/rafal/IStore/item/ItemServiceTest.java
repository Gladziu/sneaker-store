package com.rafal.IStore.item;

import com.rafal.IStore.item.model.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    void getAllItems_ItemsExist() {
        // Given
        Item item1 = new Item();
        Item item2 = new Item();

        List<Item> expectedItems = List.of(item1, item2);
        when(itemRepository.findAll()).thenReturn(expectedItems);

        // When
        List<Item> result = itemService.getAllItems();

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
    }

    @Test
    void getAllItems_ItemsNotExist() {
        // Given

        List<Item> expectedItems = Collections.emptyList();
        when(itemRepository.findAll()).thenReturn(expectedItems);

        // When
        List<Item> result = itemService.getAllItems();

        // Then
        assertThat(result).isEmpty();
    }
}
