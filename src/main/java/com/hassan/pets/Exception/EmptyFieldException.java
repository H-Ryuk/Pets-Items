package com.hassan.pets.Exception;


public class EmptyFieldException extends RuntimeException{


    public EmptyFieldException(String targetName) {
        super(targetName + " field should not be null");
    }

}
