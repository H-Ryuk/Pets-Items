package com.hassan.pets.Service;

import com.hassan.pets.Exception.EmptyWishListException;
import com.hassan.pets.Exception.RemovingItemFailedException;
import com.hassan.pets.Records.ItemWithCategoryRecord;
import com.hassan.pets.Records.UserAndItemsRecord;
import com.hassan.pets.Model.WishLists;
import com.hassan.pets.Repository.WishListRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class WishListService {


    private final WishListRepo wishListRepo;

    public WishListService(WishListRepo wishListRepo) {
        this.wishListRepo = wishListRepo;
    }


    public void addItemToWishList(UserAndItemsRecord userAndItemsRecord) {
        wishListRepo.findByUserId(userAndItemsRecord.user().getUserId())
                .ifPresentOrElse(existingWishLists -> {
                            existingWishLists.getItemsList().addAll(userAndItemsRecord.itemsList());
                            existingWishLists.setItemsList(existingWishLists.getItemsList());
                            existingWishLists.setUsers(userAndItemsRecord.user());
                            wishListRepo.save(existingWishLists);
                        },
                        () -> {
                            WishLists newWishLists = new WishLists();
                            newWishLists.setItemsList(userAndItemsRecord.itemsList());
                            newWishLists.setUsers(userAndItemsRecord.user());
                            wishListRepo.save(newWishLists);
                        });

    }


    public void removeFromWishlist(Long wishlistId, Long itemId) {
        int row = wishListRepo.removeFromWishlist(wishlistId, itemId);
        if (row == 0) {
            throw new RemovingItemFailedException(itemId);
        }
    }


    public List<ItemWithCategoryRecord> getItemsFromWishListByUserId(Long userId) {
        return wishListRepo.getItemsFromWishListByUserId(userId)
                .orElseThrow(() -> new EmptyWishListException(userId))
                .getItemsList()
                .stream()
                .map(w -> new ItemWithCategoryRecord(
                        w.getItemId(),
                        w.getName(),
                        w.getPrice(),
                        w.getDescription(),
                        w.getStock(),
                        w.getImageUrl(),
                        w.getCategory()
                )).toList();
    }


}
