package com.hassan.pets.Records;


import com.hassan.pets.Model.Items;
import com.hassan.pets.Model.Users;

import java.util.List;

public record UserAndItemsRecord(List<Items>itemsList, Users user) {
}
