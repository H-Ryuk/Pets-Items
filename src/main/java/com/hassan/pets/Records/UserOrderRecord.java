package com.hassan.pets.Records;


import java.util.List;

public record UserOrderRecord
        (String username, String address, String phoneNumber, List<OrderRecord> orderRecords) {
}
