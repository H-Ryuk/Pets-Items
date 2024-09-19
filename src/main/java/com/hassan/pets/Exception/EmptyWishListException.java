package com.hassan.pets.Exception;

public class EmptyWishListException extends RuntimeException{
    public EmptyWishListException(Long userId) {
        super("Empty wishList for this user with ID : " + userId);
    }
}
