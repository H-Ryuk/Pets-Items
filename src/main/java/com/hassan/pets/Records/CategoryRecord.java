package com.hassan.pets.Records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRecord
        (Long categoryId,

         @NotBlank(message = "category name shouldn't be null")
         String name) {
}
