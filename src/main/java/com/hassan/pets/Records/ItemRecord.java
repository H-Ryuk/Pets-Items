package com.hassan.pets.Records;

import com.hassan.pets.Model.Categories;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ItemRecord
        (Long itemId,

         @NotBlank(message = "item name shouldn't be null")
         String name,

         @NotNull(message = "item price shouldn't be null")
         BigDecimal price,

         @NotNull(message = "item description shouldn't be null")
         String description,

         @NotNull(message = "stock of items shouldn't be null")
         int stock,

         byte[] imageUrl,

         @NotNull(message = "every item should have one category")
         Categories category) {
}
