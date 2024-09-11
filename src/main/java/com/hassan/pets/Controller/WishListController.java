package com.hassan.pets.Controller;

import com.hassan.pets.Records.userAndItemsRecord;
import com.hassan.pets.Service.WishListService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/wishlist")
public class WishListController {


    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }


    @PostMapping
    public void addToWishList(@RequestBody userAndItemsRecord userAndItemsRecord){
        wishListService.addItemToWishList(userAndItemsRecord);
    }


    @DeleteMapping("{wishlistId}/item/{itemId}")
    public void removeFromWishlist(@PathVariable Long wishlistId, @PathVariable Long itemId){
        wishListService.removeFromWishlist(wishlistId, itemId);
    }


}
