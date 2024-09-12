package com.hassan.pets.Controller;

import com.hassan.pets.Records.ItemRecord;
import com.hassan.pets.Records.userAndItemsRecord;
import com.hassan.pets.Service.WishListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/wishlist")
public class WishListController {


    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }


    @PostMapping
    public ResponseEntity<String> addToWishList(@RequestBody userAndItemsRecord userAndItemsRecord){
        wishListService.addItemToWishList(userAndItemsRecord);
        return new ResponseEntity<>("The item has been successfully added to your wishlist.", HttpStatus.CREATED);
    }


    @DeleteMapping("{wishlistId}/item/{itemId}")
    public ResponseEntity<String> removeFromWishlist(@PathVariable Long wishlistId, @PathVariable Long itemId){
        wishListService.removeFromWishlist(wishlistId, itemId);
        return new ResponseEntity<>("The item has been successfully removed from your wishlist.", HttpStatus.NO_CONTENT);
    }


    @GetMapping("user/{userId}")
    public ResponseEntity<List<ItemRecord>> getItemsFromWishListByUserId(@PathVariable Long userId){
        List<ItemRecord> itemRecordList =  wishListService.getItemsFromWishListByUserId(userId);
        return new ResponseEntity<>(itemRecordList, HttpStatus.OK);
    }


}
