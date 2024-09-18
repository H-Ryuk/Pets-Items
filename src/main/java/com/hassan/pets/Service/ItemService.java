package com.hassan.pets.Service;

import com.hassan.pets.Exception.EmptyFieldException;
import com.hassan.pets.Records.ItemWithCategoryRecord;
import com.hassan.pets.Exception.TargetNotFoundException;
import com.hassan.pets.Model.Items;
import com.hassan.pets.Repository.CategoryRepo;
import com.hassan.pets.Repository.ItemRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    public ItemWithCategoryRecord addItem(ItemWithCategoryRecord itemWithCategoryRecord) {
        Items item = convertItemRecordToItem(itemWithCategoryRecord);

        categoryRepo.findByName(item.getCategory().getName())
                .ifPresentOrElse(
                        item::setCategory,
                        () -> item.setCategory(categoryRepo.save(item.getCategory()))
                );

        return Optional.of(itemRepo.save(item))
                .map(itemOptional -> new ItemWithCategoryRecord(
                        itemOptional.getItemId(),
                        itemOptional.getName(),
                        itemOptional.getPrice(),
                        itemOptional.getDescription(),
                        itemOptional.getStock(),
                        itemOptional.getImageUrl(),
                        itemOptional.getCategory()
                )).get();
    }


    public List<ItemWithCategoryRecord> getAll() {
        return itemRepo.findItemCategoryDetails();
    }


    public Page<ItemWithCategoryRecord> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return itemRepo.findItemCategoryDetails(pageable);
    }


    public List<ItemWithCategoryRecord> getByName(String name) {
        List<ItemWithCategoryRecord> itemWithCategoryRecordList = itemRepo.findItemCategoryDetails(name);
        if (itemWithCategoryRecordList.isEmpty()) {
            throw new TargetNotFoundException(name);
        }
        return itemWithCategoryRecordList;
    }


    public ItemWithCategoryRecord getById(Long itemId) {
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


    public ItemWithCategoryRecord updateItem(ItemWithCategoryRecord itemWithCategoryRecord) {
        Items newItem = convertItemRecordToItem(itemWithCategoryRecord);

        itemRepo.findById(newItem.getItemId())
                .ifPresentOrElse(items -> {
                            categoryRepo.findByName(newItem.getCategory().getName())
                                    .ifPresentOrElse(newItem::setCategory,
                                            () -> {
                                                if (itemWithCategoryRecord.category().getName() == null)
                                                    throw new EmptyFieldException(fieldName);
                                                else
                                                    categoryRepo.save(itemWithCategoryRecord.category());
                                            });

                            itemRepo.save(newItem);
                        },
                        () -> {
                            throw new TargetNotFoundException(targetName, newItem.getItemId());
                        });


        return itemWithCategoryRecord;
    }


    public Items convertItemRecordToItem(ItemWithCategoryRecord itemWithCategoryRecord) {
        return new Items(
                itemWithCategoryRecord.itemId(),
                itemWithCategoryRecord.name(),
                itemWithCategoryRecord.description(),
                itemWithCategoryRecord.price(),
                itemWithCategoryRecord.stock(),
                itemWithCategoryRecord.imageUrl(),
                itemWithCategoryRecord.category()
        );
    }


}
