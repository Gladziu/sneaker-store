package com.rafal.IStore.shoppingbasket;

import com.rafal.IStore.shoppingbasket.model.BasketInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface BasketInfoRepository extends JpaRepository<BasketInfo, UUID> {

    BasketInfo findByUserId(UUID id);

    void deleteByUserId(UUID id);
}
