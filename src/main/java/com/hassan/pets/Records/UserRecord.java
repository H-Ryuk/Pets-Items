package com.hassan.pets.Records;


import com.hassan.pets.Model.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserRecord
        (Long userId,

         @NotBlank(message = "Username shouldn't be null")
         String username,

         @NotBlank(message = "Password shouldn't be null")
         String password,

         @Email(message = "Invalid email address")
         String email,

         @NotNull(message = "Address shouldn't be null")
         String address,

         @Pattern(regexp = "^\\d{10}$", message = "Invalid mobile number entered")
         String phoneNumber,

         @NotNull(message = "Role shouldn't be null")
         Users.UserRole role ) {
}

