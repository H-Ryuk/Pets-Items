package com.hassan.pets.Exception;

public class LogInFailedException extends RuntimeException{


    public LogInFailedException(String userEmail) {
        super("Invalid credentials please try again with a valid email and password");
    }
}
