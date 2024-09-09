package com.hassan.pets.Contoller;

import com.hassan.pets.Records.CategoryRecord;
import com.hassan.pets.Records.ItemRecord;
import com.hassan.pets.Exception.TargetNotFoundException;
import com.hassan.pets.Model.Categories;
import com.hassan.pets.Model.Items;
import com.hassan.pets.Service.CategoryService;
import com.hassan.pets.Service.ItemService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/items")
public class ItemsController {


    private final ItemService itemService;
    private final CategoryService categoryService;


    public ItemsController(ItemService itemService, CategoryService categoryService) {
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
    public ResponseEntity<ItemRecord> updateItem(@RequestBody ItemRecord itemRecord) {
        ItemRecord item = itemService.updateItem(itemRecord);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }


    /////////////// Categories methods ////////////////

    @GetMapping("category")
    public List<CategoryRecord> getAllCategories() {
        return categoryService.getAllCategories();
    }


    @PutMapping("category")
    public ResponseEntity<String> updateCategory(@RequestBody Categories category) {
        categoryService.updateCategory(category);
        return new
                ResponseEntity<>("Category with ID: " + category.getCategoryId() + " get successfully updated",
                HttpStatus.OK);
    }


}
