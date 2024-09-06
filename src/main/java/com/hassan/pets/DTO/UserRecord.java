package com.hassan.pets.DTO;



public record UserRecord
        (Long userId, String username,
         String password, String email,
         String address, String phoneNumber) {
}

