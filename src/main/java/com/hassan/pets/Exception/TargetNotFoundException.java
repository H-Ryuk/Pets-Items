package com.hassan.pets.Exception;

public class TargetNotFoundException extends RuntimeException{

    public TargetNotFoundException(Long targetId){
        super("Target not found with this id : " + targetId);
    }

}
