package com.hassan.pets.repository;

import com.hassan.pets.DTO.ItemRecord;
import com.hassan.pets.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface ItemRepo extends JpaRepository<Items,Integer> {

    @Query("SELECT new com.hassan.pets.DTO.ItemRecord(i.itemId, i.name, i.price, i.stock,i.imageUrl, c.name, c.description) " +
            "FROM Items i JOIN i.category c where i.name like :name% ")
    List<ItemRecord> findItemCategoryDetails(String name);


    @Query("select new com.hassan.pets.DTO.ItemRecord(i.itemId, i.name, i.price, i.stock,i.imageUrl, c.name, c.description) " +
            "from Items i join i.category c where i.itemId = :id")
    ItemRecord findItemCategoryDetails(int id);


    @Query("select new com.hassan.pets.DTO.ItemRecord(i.itemId, i.name, i.price, i.stock,i.imageUrl, c.name, c.description) " +
            "from Items i join i.category c")
    List<ItemRecord> findItemCategoryDetails();

}
