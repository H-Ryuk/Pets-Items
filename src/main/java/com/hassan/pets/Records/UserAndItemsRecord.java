package com.hassan.pets.Records;


import com.hassan.pets.Model.Items;
import com.hassan.pets.Model.Users;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserAndItemsRecord(
        @NotNull(message = "not be null")
        List<Items> itemsList,

        @NotNull(message = "not be null")
        Users user) {
}
