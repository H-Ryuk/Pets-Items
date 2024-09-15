package com.hassan.pets.Records;

import java.math.BigDecimal;


public record OrderDetailsRecord
        (Long detailOrderId, int quantity, BigDecimal price, Long itemId,
         String itemName, BigDecimal itemPrice, String itemCategoryName) {
}
