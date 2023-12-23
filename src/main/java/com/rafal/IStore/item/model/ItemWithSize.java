package com.rafal.IStore.item.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemWithSize {

    private final Item item;
    private final int size;
}
