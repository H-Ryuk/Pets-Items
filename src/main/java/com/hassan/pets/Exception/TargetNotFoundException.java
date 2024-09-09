package com.hassan.pets.Exception;

public class TargetNotFoundException extends RuntimeException{

    public TargetNotFoundException(String targetName, Long targetId){
        super(targetName + " with ID: " + targetId + " not found.");
    }

    public TargetNotFoundException(String targetId){
        super("Item with name : " + targetId + " not found.");
    }


}
