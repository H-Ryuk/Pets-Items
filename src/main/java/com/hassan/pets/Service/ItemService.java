package com.hassan.pets.Service;

import com.hassan.pets.Records.ItemRecord;
import com.hassan.pets.Exception.TargetNotFoundException;
import com.hassan.pets.Model.Categories;
import com.hassan.pets.Model.Items;
import com.hassan.pets.Repository.CategoryRepo;
import com.hassan.pets.Repository.ItemRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class ItemService {


    private final ItemRepo itemRepo;
    private final CategoryRepo categoryRepo;

    public ItemService(ItemRepo itemRepo, CategoryRepo categoryRepo) {
        this.itemRepo = itemRepo;
        this.categoryRepo = categoryRepo;
    }


    public ItemRecord addItem(ItemRecord itemRecord) {
        Items item = convertItemRecordToItem(itemRecord);

        categoryRepo.findByName(item.getCategory().getName())
                .ifPresentOrElse(
                        item::setCategory,
                () -> item.setCategory(categoryRepo.save(item.getCategory()))
        );

        return Optional.of(itemRepo.save(item))
                .map(itemOptional -> new ItemRecord(
                        itemOptional.getItemId(),
                        itemOptional.getName(),
                        itemOptional.getPrice(),
                        itemOptional.getDescription(),
                        itemOptional.getStock(),
                        itemOptional.getImageUrl(),
                        itemOptional.getCategory()
                )).get();
    }




    public List<ItemRecord> getAll() {
        return itemRepo.findItemCategoryDetails();
    }


    public List<ItemRecord> getByName(String name) {
        return itemRepo.findItemCategoryDetails(name);
    }


    public ItemRecord getById(Long itemId) {
        return itemRepo.findItemCategoryDetails(itemId)
                .orElseThrow(() -> new TargetNotFoundException(itemId));
    }


    public void deleteItem(Long itemId) {
        itemRepo.deleteById(itemId);
    }


    public ItemRecord updateItem(ItemRecord itemRecord) {
        Items newItem = convertItemRecordToItem(itemRecord);
        itemRepo.save(newItem);
        newItem.setCategory(
                categoryRepo.findById(newItem.getCategory().getCategoryId())
                        .orElse(null)
        );
        return itemRecord;
    }


    public Items convertItemRecordToItem(ItemRecord itemRecord) {
        return new Items(
                itemRecord.itemId(),
                itemRecord.name(),
                itemRecord.description(),
                itemRecord.price(),
                itemRecord.stock(),
                itemRecord.imageUrl(),
                itemRecord.category()
        );
    }


}
