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
        try {
            ItemRecord itemsRecord = itemService.getById(itemId);
            return new ResponseEntity<>(itemsRecord, HttpStatus.OK);

        } catch (TargetNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("name/{ItemName}")
    public List<ItemRecord> getByName(@PathVariable String ItemName) {
        return itemService.getByName(ItemName);
    }


    @DeleteMapping("{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
    }


    @PutMapping
    public ItemRecord updateItem(@RequestBody ItemRecord itemRecord) {
        return itemService.updateItem(itemRecord);
    }


    /////////////// Categories methods ////////////////

    @GetMapping("categories")
    public List<CategoryRecord> getAllCategories() {
        return categoryService.getAllCategories();
    }


    @PutMapping("categories")
    public void updateCategory(@RequestBody Categories category) {
        categoryService.updateCategory(category);
    }


}
