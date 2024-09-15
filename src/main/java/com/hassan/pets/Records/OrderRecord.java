package com.hassan.pets.Records;

import com.hassan.pets.Model.Orders;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record OrderRecord(
        Long orderid, LocalDateTime orderdate,
        Orders.OrderStatus status, BigDecimal totalamount) {
}
