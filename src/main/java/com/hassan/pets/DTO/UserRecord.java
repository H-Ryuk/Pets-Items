package com.hassan.pets.DTO;



public record UserRecord
        (int userId, String username,
         String password, String email,
         String address, String phoneNumber) {
}

