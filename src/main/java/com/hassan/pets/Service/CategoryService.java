package com.hassan.pets.Service;

import com.hassan.pets.Exception.TargetNotFoundException;
import com.hassan.pets.Records.CategoryRecord;
import com.hassan.pets.Model.Categories;
import com.hassan.pets.Repository.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final String targetName = "Category";
    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }


    public void updateCategory(Long categoryId, CategoryRecord categoryRecord) {
        Categories category = convertCategoryRecordToCategories(categoryRecord);
        if (categoryId != null) {
            categoryRepo.findById(categoryId)
                    .ifPresentOrElse(categories ->
                            {
                                categories.setName(categoryRecord.name());
                                categoryRepo.save(categories);
                            },
                            () -> {
                                throw new TargetNotFoundException(targetName, categoryId);
                            });
        }
    }


    public List<CategoryRecord> getAllCategories() {
        return categoryRepo.getAllCategories();
    }


    public Categories convertCategoryRecordToCategories(CategoryRecord categoryRecord) {
        return new Categories(
                categoryRecord.categoryId(),
                categoryRecord.name()
        );
    }


}
