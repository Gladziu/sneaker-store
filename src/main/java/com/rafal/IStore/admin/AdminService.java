package com.rafal.IStore.admin;

import com.rafal.IStore.item.model.Item;

interface AdminService {

    void deleteItem(Long itemId);

    void editItem(Item item);

    void addItem(Item item);

    Item findItemById(Long itemId);

}
