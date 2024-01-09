package com.rafal.sneakerstoreapp.admin;

import com.rafal.sneakerstoreapp.item.model.Item;

interface AdminService {

    void deleteItem(Long itemId);

    void editItem(Item item);

    void addItem(Item item);

    Item findItemById(Long itemId);

}
