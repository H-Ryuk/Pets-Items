package com.hassan.pets.DTO;

import java.math.BigDecimal;

public record ItemRecord( Long itemId, String itemName, BigDecimal price, int stock,
                          byte[] itemImage, String categoryName, String categoryDescription) {
}
