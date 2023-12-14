package com.rafal.IStore.repository.basket;

import com.rafal.IStore.model.basket.BasketInfo;
import com.rafal.IStore.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketInfoRepository extends JpaRepository<BasketInfo, Long> {
    BasketInfo findByUser(User user);

    void deleteByUser(User user);
}
