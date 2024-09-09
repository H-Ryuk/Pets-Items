package com.hassan.pets.Exception;

public class RemovingItemFailedException extends RuntimeException{

    public RemovingItemFailedException(Long itemId, Long cartId) {
        super("Removing item with ID: " + itemId + " from cart with ID: "  + cartId + " failed");
    }
}
