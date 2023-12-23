/*
package com.rafal.IStore.repository;

import com.rafal.IStore.item.model.Item;
import com.rafal.IStore.item.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void testItemRepository_SavaAll(){
        //Given
        Item item = new Item();
        item.setName("test");
        item.setPrice(BigDecimal.valueOf(99.99));
        item.setUrlImage("ImageUrl");

        //When
        Item savedItem = itemRepository.save(item);

        //Then
        assertNotNull(savedItem);
    }
}*/
