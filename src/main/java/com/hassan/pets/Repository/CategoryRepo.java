package com.hassan.pets.Repository;

import com.hassan.pets.Records.CategoryRecord;
import com.hassan.pets.Model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Categories,Long> {



    @Query("select c from Categories c where name like :name")
    Optional<Categories> findByName(@Param("name") String name);



    @Query("select new com.hassan.pets.Records.CategoryRecord" +
            "(c.categoryId, c.name) from Categories c")
    List<CategoryRecord> getAllCategories();




}
