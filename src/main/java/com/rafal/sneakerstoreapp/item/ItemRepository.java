package com.rafal.sneakerstoreapp.item;

import com.rafal.sneakerstoreapp.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
