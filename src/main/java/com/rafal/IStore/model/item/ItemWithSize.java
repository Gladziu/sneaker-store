package com.rafal.IStore.model.item;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemWithSize {

    private final Item item;
    private final int size;
}
