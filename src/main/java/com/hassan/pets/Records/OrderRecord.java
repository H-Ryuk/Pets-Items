package com.hassan.pets.Records;

import com.hassan.pets.Model.Orders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


public record OrderRecord(
        Long orderid, LocalDate orderdate,
        Orders.OrderStatus status, BigDecimal totalamount) {
}
