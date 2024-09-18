package com.hassan.pets.Controller;

import com.hassan.pets.Records.ItemRecord;
import com.hassan.pets.Records.ItemWithCategoryRecord;
import com.hassan.pets.Service.CategoryService;
import com.hassan.pets.Service.ItemService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/item")
public class ItemController {


    private final ItemService itemService;
    private final CategoryService categoryService;


    public ItemController(ItemService itemService, CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }


    @PostMapping
    public ResponseEntity<String> addItem(@RequestBody @Valid ItemWithCategoryRecord itemWithCategoryRecord) {
        ItemWithCategoryRecord item = itemService.addItem(itemWithCategoryRecord);
        return new
                ResponseEntity<>("The item " + item.name() + " was created successfully with ID : " + item.itemId(),
                HttpStatus.CREATED);
    }


    @GetMapping
    public List<ItemWithCategoryRecord> getAll() {
        return itemService.getAll();
    }



    ////// Testing pagination  ///////////////////
    @GetMapping("pagination")
    public ResponseEntity<Page<ItemWithCategoryRecord>> getAll(
            @RequestParam int page,
            @RequestParam int size) {
        Page<ItemWithCategoryRecord> items = itemService.getAll(page, size);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
    ///////////////////////////////////////////////




    @GetMapping("{itemId}")
    public ResponseEntity<?> getById(@PathVariable Long itemId) {
        ItemWithCategoryRecord itemsRecord = itemService.getById(itemId);
        return new ResponseEntity<>(itemsRecord, HttpStatus.OK);

    }


    @GetMapping("name/{itemName}")
    public ResponseEntity<List<ItemWithCategoryRecord>> getByName(@PathVariable String itemName) {
        List<ItemWithCategoryRecord> listItem = itemService.getByName(itemName);
        return new ResponseEntity<>(listItem, HttpStatus.OK);
    }


    @GetMapping("category/{categoryName}")
    public ResponseEntity<List<ItemRecord>> getItemsByCategoryName(@PathVariable String categoryName){
        List<ItemRecord> itemRecordList = categoryService.getItemsByCategoryName(categoryName);
        return new ResponseEntity<>(itemRecordList, HttpStatus.OK);
    }



    @DeleteMapping("{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return new ResponseEntity<>("The item has been successfully removed", HttpStatus.NO_CONTENT);
    }


    @PutMapping
    public ResponseEntity<ItemWithCategoryRecord> updateItem(@RequestBody @Valid ItemWithCategoryRecord itemWithCategoryRecord) {
        ItemWithCategoryRecord item = itemService.updateItem(itemWithCategoryRecord);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }





}
