package com.hassan.pets.DTO;

import java.math.BigDecimal;

public record ItemRecord( int itemId, String itemName, BigDecimal price, int stock,
                          byte[] itemImage, String categoryName, String categoryDescription) {
}
