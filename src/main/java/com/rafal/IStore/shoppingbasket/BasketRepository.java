package com.rafal.IStore.shoppingbasket;

import com.rafal.IStore.item.model.Item;
import com.rafal.IStore.shoppingbasket.model.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
interface BasketRepository extends JpaRepository<BasketItem, UUID> {
    List<BasketItem> findAllByItem(Item item);

    List<BasketItem> findAllByUserId(UUID id);

    void deleteAllByUserId(UUID id);

    List<BasketItem> findAllByUserIdAndItemAndSize(UUID userId, Item item, int size);
}
