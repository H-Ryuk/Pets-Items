package com.hassan.pets.Exception;

public class CategoryNotFoundException extends RuntimeException{


    public CategoryNotFoundException(String categoryName) {
        super("Category [" + categoryName + "] does not exist");
    }
}
