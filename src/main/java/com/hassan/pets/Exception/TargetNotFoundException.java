package com.hassan.pets.exception;

public class TargetNotFoundException extends RuntimeException{

    public TargetNotFoundException(Long targetId){
        super("Not found with this id : " + targetId);
    }

}
