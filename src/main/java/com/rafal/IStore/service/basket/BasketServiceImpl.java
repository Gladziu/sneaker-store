package com.rafal.IStore.service.basket;

import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.model.item.ItemWithSize;
import com.rafal.IStore.model.user.User;
import com.rafal.IStore.repository.ItemRepository;
import com.rafal.IStore.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BasketServiceImpl implements BasketService {

    private final ItemRepository itemRepository;
    private final BasketItemService basketItemService;
    private final UserService userService;

    public BasketServiceImpl(ItemRepository itemRepository, BasketItemService basketItemService, UserService userService) {
        this.itemRepository = itemRepository;
        this.basketItemService = basketItemService;
        this.userService = userService;
    }

    @Override
    public void itemOperation(Long itemId, int size, BasketOperation basketOperation, Authentication authentication) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isPresent()) {
            User user = userService.getCurrentUser(authentication);
            Item item = optionalItem.get();
            ItemWithSize itemWithSize = new ItemWithSize(item, size);
            switch (basketOperation) {
                case INCREASE -> basketItemService.addItem(itemWithSize, user);
                case DECREASE -> basketItemService.removeItem(itemWithSize, user);
                case REMOVE -> basketItemService.removeAllItems(itemWithSize, user);
                default -> throw new IllegalArgumentException();
            }
        }
    }

}
