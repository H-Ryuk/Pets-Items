package com.hassan.pets.Records;

import java.math.BigDecimal;

public record ItemRecord(
        String name,
        String description,
        BigDecimal price,
        int stock,
        byte[] imageUrl) {
}
