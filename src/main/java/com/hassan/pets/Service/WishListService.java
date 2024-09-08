package com.hassan.pets.Service;

import com.hassan.pets.Records.userAndItemsRecord;
import com.hassan.pets.Model.WishLists;
import com.hassan.pets.Repository.WishListRepo;
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
