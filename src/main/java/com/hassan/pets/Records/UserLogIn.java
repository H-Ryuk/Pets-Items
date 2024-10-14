package com.hassan.pets.Records;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserLogIn (

        @Email(message = "Invalid email address")
        @NotNull(message = "Email address should not be null")
        String email,

        @NotNull(message = "Password should not be null")
        String password ){
}
