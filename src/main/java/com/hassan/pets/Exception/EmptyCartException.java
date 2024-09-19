package com.hassan.pets.Exception;

public class EmptyCartException extends RuntimeException{

    public EmptyCartException(Long userId) {
        super("Empty cart for this user with ID : " + userId);
    }
}
