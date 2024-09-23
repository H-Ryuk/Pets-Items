package com.hassan.pets.Records;

import com.hassan.pets.Model.Orders;

import java.time.LocalDate;

public record DateStatusOrderRecord(
        Long orderId,
        LocalDate orderDate,
        Orders.OrderStatus status) {
}
