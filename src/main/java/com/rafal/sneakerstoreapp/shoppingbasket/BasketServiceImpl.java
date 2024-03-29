package com.rafal.sneakerstoreapp.shoppingbasket;

import com.rafal.sneakerstoreapp.item.ItemRepository;
import com.rafal.sneakerstoreapp.item.model.Item;
import com.rafal.sneakerstoreapp.item.model.ItemWithSize;
import com.rafal.sneakerstoreapp.user.UserDto;
import com.rafal.sneakerstoreapp.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
class BasketServiceImpl implements BasketService {
    private final static int MAX_SIZE = 52;
    private final static int MIN_SIZE = 35;
    private final ItemRepository itemRepository;
    private final BasketItemService basketItemService;
    private final BasketInfoService basketInfoService;
    private final UserService userService;

    public BasketServiceImpl(ItemRepository itemRepository, BasketItemService basketItemService, BasketInfoService basketInfoService, UserService userService) {
        this.itemRepository = itemRepository;
        this.basketItemService = basketItemService;
        this.basketInfoService = basketInfoService;
        this.userService = userService;
    }

    @Override
    public void itemOperation(Long itemId, int size, BasketOperation basketOperation, Authentication authentication) {
        if (!isSizeValid(size)) {
            return;
        }
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        optionalItem.ifPresent(item -> processBasketOperation(item, size, basketOperation, authentication));
    }

    private void processBasketOperation(Item item, int size, BasketOperation basketOperation, Authentication authentication) {
        ItemWithSize itemWithSize = new ItemWithSize(item, size);
        UserDto currentUser = userService.getCurrentUser(authentication);
        UUID currentUserId = currentUser.getId();

        if (BasketOperation.INCREASE == basketOperation) {
            basketItemService.addItem(itemWithSize, currentUserId);
        }
        if (BasketOperation.DECREASE == basketOperation) {
            basketItemService.removeItem(itemWithSize, currentUserId);
        }
        if (BasketOperation.REMOVE == basketOperation) {
            basketItemService.removeAllIdenticalItems(itemWithSize, currentUserId);
        }

        basketInfoService.updateBasketInfo(currentUserId);
    }

    private boolean isSizeValid(int size) {
        return size <= MAX_SIZE && size >= MIN_SIZE;
    }
}
