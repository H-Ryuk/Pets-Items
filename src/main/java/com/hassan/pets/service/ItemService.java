package com.hassan.pets.service;

import com.hassan.pets.DTO.ItemRecord;
import com.hassan.pets.model.Categories;
import com.hassan.pets.model.Items;
import com.hassan.pets.repository.CategoryRepo;
import com.hassan.pets.repository.ItemRepo;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class ItemService {


    private final ItemRepo itemRepo;
    private final CategoryRepo categoryRepo;

    public ItemService(ItemRepo itemRepo, CategoryRepo categoryRepo) {
        this.itemRepo = itemRepo;
        this.categoryRepo = categoryRepo;
    }


    public ItemRecord addItem(Items item) {


        Categories exsitingCategory = categoryRepo.findByName(item.getCategory().getName());

        if (exsitingCategory == null) {
            item.setCategory(categoryRepo.save(item.getCategory()));
        } else {
            item.setCategory(exsitingCategory);
        }


        itemRepo.save(item);

        return new ItemRecord(
                item.getItemId(),
                item.getName(),
                item.getPrice(),
                item.getStock(),
                item.getImageUrl(),
                item.getCategory().getName(),
                item.getCategory().getDescription()
        );
    }


    public List<ItemRecord> getAll() {
        return itemRepo.findItemCategoryDetails();
    }


    public List<ItemRecord> getByName(String name) {
        return itemRepo.findItemCategoryDetails(name);
    }


    public ItemRecord getById(Long itemId) {
        return itemRepo.findItemCategoryDetails(itemId);
    }


    public void deleteItem(Long itemId) {
        itemRepo.deleteById(itemId);
    }


    public ItemRecord updateItem(Items newItem) {
        itemRepo.save(newItem);
        newItem.setCategory(
                categoryRepo.findById(newItem.getCategory().getCategoryId())
                        .orElse(null)
        );

        return new ItemRecord(
                newItem.getItemId(),
                newItem.getName(),
                newItem.getPrice(),
                newItem.getStock(),
                newItem.getImageUrl(),
                newItem.getCategory().getName(),
                newItem.getCategory().getDescription()
        );
    }


}
