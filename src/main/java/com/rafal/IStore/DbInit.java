package com.rafal.IStore;

import com.rafal.IStore.model.Item;
import com.rafal.IStore.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;


@Configuration
public class DbInit implements CommandLineRunner {

    private final ItemRepository itemRepository;

    @Autowired
    public DbInit(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        itemRepository.saveAll(List.of(
         new Item("Jordan 1 Chicago", new BigDecimal(2100), "https://images.stockx.com/images/Air-Jordan-1-Retro-Chicago-2015-Product.jpg?fit=fill&bg=FFFFFF&w=700&h=500&fm=webp&auto=compress&q=90&dpr=2&trim=color&updated_at=1607656901"),
         new Item("LV Trainer", new BigDecimal(1750), "https://images.stockx.com/images/Louis-Vuitton-Trainer-Monogram-Demin-Product.jpg?fit=fill&bg=FFFFFF&w=700&h=500&fm=webp&auto=compress&q=90&dpr=2&trim=color&updated_at=1655385933"),
         new Item("Dunk Panda", new BigDecimal(550), "https://images.stockx.com/360/Nike-Dunk-Low-White-Black-2021-W/Images/Nike-Dunk-Low-White-Black-2021-W/Lv2/img01.jpg?fm=jpg&auto=compress&w=480&dpr=2&updated_at=1635341330&h=320&q=75")
        ));
    }
}
