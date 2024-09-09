package com.hassan.pets.Service;

import com.hassan.pets.Exception.EmptyFieldException;
import com.hassan.pets.Records.ItemRecord;
import com.hassan.pets.Exception.TargetNotFoundException;
import com.hassan.pets.Model.Items;
import com.hassan.pets.Repository.CategoryRepo;
import com.hassan.pets.Repository.ItemRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ItemService {


    private final String targetName = "Item";
    private final String fieldName = "Category Name";

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


    public Page<ItemRecord> getAll(int page ,int size) {
        Pageable pageable = PageRequest.of(page, size);
        return itemRepo.findItemCategoryDetails(pageable);
    }



    public List<ItemRecord> getByName(String name) {
        List<ItemRecord> itemRecordList = itemRepo.findItemCategoryDetails(name);
        if (itemRecordList.isEmpty()) {
            throw new TargetNotFoundException(name);
        }
        return itemRecordList;
    }




    public ItemRecord getById(Long itemId) {
        return itemRepo.findItemCategoryDetails(itemId)
                .orElseThrow(() -> new TargetNotFoundException(targetName, itemId));
    }




    public void deleteItem(Long itemId) {
        itemRepo.findById(itemId)
                .ifPresentOrElse(itemRepo::delete,
                        () -> {
                            throw new TargetNotFoundException(targetName, itemId);
                        });
    }




    public ItemRecord updateItem(ItemRecord itemRecord) {
        Items newItem = convertItemRecordToItem(itemRecord);

        categoryRepo.findByName(newItem.getCategory().getName())
                .ifPresentOrElse(newItem::setCategory,
                        () -> {
                            if (itemRecord.category().getName() == null)
                                throw new EmptyFieldException(fieldName);
                            else
                                categoryRepo.save(itemRecord.category());
                        });

        itemRepo.save(newItem);
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
