package com.hassan.pets.Controller;

import com.hassan.pets.Records.ItemWithCategoryRecord;
import com.hassan.pets.Records.UserAndItemsRecord;
import com.hassan.pets.Service.OrderService;
import com.hassan.pets.Service.WishListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/wishlist")
public class WishListController {


    private final WishListService wishListService;
    private final OrderService orderService;

    public WishListController(WishListService wishListService, OrderService orderService) {
        this.wishListService = wishListService;
        this.orderService = orderService;
    }


    @PostMapping
    public ResponseEntity<String> addToWishList(@RequestBody UserAndItemsRecord userItemsData){
        wishListService.addItemToWishList(userItemsData);
        return new ResponseEntity<>("The item has been successfully added to your wishlist.", HttpStatus.CREATED);
    }



    @PostMapping("user/{userId}")
    public ResponseEntity<String> addOrderDetails(@PathVariable Long userId) {
        orderService.addOrderDetails("wishlist", userId);
        return new ResponseEntity<>("Items purchased successfully", HttpStatus.OK);
    }



    @DeleteMapping("{wishlistId}/item/{itemId}")
    public ResponseEntity<String> removeFromWishlist(@PathVariable Long wishlistId, @PathVariable Long itemId){
        wishListService.removeFromWishlist(wishlistId, itemId);
        return new ResponseEntity<>("The item has been successfully removed from your wishlist.", HttpStatus.NO_CONTENT);
    }



    @GetMapping("user/{userId}")
    public ResponseEntity<List<ItemWithCategoryRecord>> getItemsFromWishListByUserId(@PathVariable Long userId){
        List<ItemWithCategoryRecord> itemWithCategoryRecordList =  wishListService.getItemsFromWishListByUserId(userId);
        return new ResponseEntity<>(itemWithCategoryRecordList, HttpStatus.OK);
    }


}
