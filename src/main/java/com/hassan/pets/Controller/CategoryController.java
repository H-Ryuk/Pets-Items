package com.hassan.pets.Controller;

import com.hassan.pets.Records.CategoryRecord;
import com.hassan.pets.Repository.CategoryRepo;
import com.hassan.pets.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {


    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }




    @GetMapping
    public List<CategoryRecord> getAllCategories() {
        return categoryService.getAllCategories();
    }



    @GetMapping("/{categoryName}")
    public ResponseEntity<CategoryRecord> getCategoryByName(@PathVariable String categoryName){
        CategoryRecord category =categoryService.getCategroyByName(categoryName);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }



    @PutMapping("/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId, @RequestBody @Valid CategoryRecord categoryRecord) {
        categoryService.updateCategory(categoryId, categoryRecord);
        return new
                ResponseEntity<>("Category with ID: " + categoryId + " get successfully updated",
                HttpStatus.OK);
    }






}
