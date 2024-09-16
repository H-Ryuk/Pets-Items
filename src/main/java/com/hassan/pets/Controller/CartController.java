package com.hassan.pets.Controller;

import com.hassan.pets.Model.Items;
import com.hassan.pets.Records.UserAndItemsRecord;
import com.hassan.pets.Repository.CartRepo;
import com.hassan.pets.Service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/cart")
public class CartController {


    private final CartService cartService;


    public CartController(CartService cartService, CartRepo cartRepo) {
        this.cartService = cartService;

    }


    @PostMapping
    public ResponseEntity<String> addToCart(@RequestBody @Valid UserAndItemsRecord addToCartRequest) {
        cartService.addToCart(addToCartRequest.itemsList(), addToCartRequest.user());
        return new ResponseEntity<>("Items successfully added to cart", HttpStatus.CREATED);
    }



    @GetMapping("user/{userId}")
    public ResponseEntity<List<Items>> getAllItemsFromCartByUserId(@PathVariable Long userId){
        List<Items> itemsList = cartService.getAllItemsFromCartByUserId(userId);
        return new ResponseEntity<>(itemsList, HttpStatus.OK);
    }



    @DeleteMapping("{cartId}/item/{itemId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable Long itemId, @PathVariable Long cartId) {
        cartService.removeItemFromCart(itemId, cartId);
        return new
                ResponseEntity<>("Item with ID: "+ itemId + " has been successfully removed from your cart.",
                HttpStatus.NO_CONTENT);
    }





}
