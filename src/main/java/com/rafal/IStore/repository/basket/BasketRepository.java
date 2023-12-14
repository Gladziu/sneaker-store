package com.rafal.IStore.repository.basket;

import com.rafal.IStore.model.basket.BasketItem;
import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<BasketItem, Long> {
    BasketItem findByUserAndItemAndSize(User user, Item item, int size);

    List<BasketItem> findAllByItem(Item item);

    void deleteAllByUser(User user);

    List<BasketItem> findAllByUser(User user);
}
