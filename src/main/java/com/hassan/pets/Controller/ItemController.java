package com.hassan.pets.Contoller;

import com.hassan.pets.Records.CategoryRecord;
import com.hassan.pets.Records.ItemRecord;
import com.hassan.pets.Model.Categories;
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
    public ResponseEntity<String> addItem(@RequestBody @Valid ItemRecord itemRecord) {
        ItemRecord item = itemService.addItem(itemRecord);
        return new
                ResponseEntity<>("Item " + item.name() + " successfully created with ID : " + item.itemId(),
                HttpStatus.CREATED);
    }


    @GetMapping
    public List<ItemRecord> getAll() {
        return itemService.getAll();
    }



    ////// Testing pagination  ///////////////////
    @GetMapping("pagination")
    public ResponseEntity<Page<ItemRecord>> getAll(
            @RequestParam int page,
            @RequestParam int size) {
        Page<ItemRecord> items = itemService.getAll(page, size);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
    ///////////////////////////////////////////////




    @GetMapping("{itemId}")
    public ResponseEntity<?> getById(@PathVariable Long itemId) {
        ItemRecord itemsRecord = itemService.getById(itemId);
        return new ResponseEntity<>(itemsRecord, HttpStatus.OK);

    }


    @GetMapping("name/{itemName}")
    public ResponseEntity<List<ItemRecord>> getByName(@PathVariable String itemName) {
        List<ItemRecord> listItem = itemService.getByName(itemName);
        return new ResponseEntity<>(listItem, HttpStatus.OK);
    }


    @DeleteMapping("{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }


    @PutMapping
    public ResponseEntity<ItemRecord> updateItem(@RequestBody @Valid ItemRecord itemRecord) {
        ItemRecord item = itemService.updateItem(itemRecord);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }




    /////////////// Categories methods ////////////////

    @GetMapping("category")
    public List<CategoryRecord> getAllCategories() {
        return categoryService.getAllCategories();
    }


    @PutMapping("category/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId, @RequestBody @Valid CategoryRecord categoryRecord) {
        categoryService.updateCategory(categoryId, categoryRecord);
        return new
                ResponseEntity<>("Category with ID: " + categoryId + " get successfully updated",
                HttpStatus.OK);
    }


}
