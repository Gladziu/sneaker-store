package com.rafal.IStore.model.item;


import lombok.Getter;

@Getter
public class ItemWithSize {
    private Item item;
    private int size;

    public ItemWithSize(Item item, int size) {
        this.item = item;
        this.size = size;
    }
}
