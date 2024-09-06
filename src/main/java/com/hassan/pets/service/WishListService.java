package com.hassan.pets.service;

import com.hassan.pets.DTO.userAndItemsRecord;
import com.hassan.pets.model.WishLists;
import com.hassan.pets.repository.WishListRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class WishListService {


    private final WishListRepo wishListRepo;

    public WishListService(WishListRepo wishListRepo) {
        this.wishListRepo = wishListRepo;
    }





    public void addItemToWishList(userAndItemsRecord userAndItemsRecord) {
        WishLists wishListsExist =
                wishListRepo.findByUserId(userAndItemsRecord.user().getUserId());

        if (wishListsExist != null) {
            wishListsExist.getItemsList().addAll(userAndItemsRecord.itemsList());
            wishListsExist.setItemsList(wishListsExist.getItemsList());
            wishListsExist.setUsers(userAndItemsRecord.user());
            wishListRepo.save(wishListsExist);
        } else {
            WishLists wishLists = new WishLists();
            wishLists.setItemsList(userAndItemsRecord.itemsList());
            wishLists.setUsers(userAndItemsRecord.user());
            wishListRepo.save(wishLists);
        }
    }


    public void removeFromWishlist(Long wishlistId, Long itemId) {
        wishListRepo.removeFromWishlist(wishlistId, itemId);
    }
}
