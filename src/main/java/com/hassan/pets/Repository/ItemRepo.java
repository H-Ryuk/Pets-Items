package com.hassan.pets.Repository;

import com.hassan.pets.Records.ItemRecord;
import com.hassan.pets.Model.Items;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ItemRepo extends JpaRepository<Items, Long> {


    @Query("select new com.hassan.pets.Records.ItemRecord" +
            "(i.itemId, i.name, i.price, i.description, i.stock,i.imageUrl, i.category) " +
            "FROM Items i where i.name like :name% ")
    List<ItemRecord> findItemCategoryDetails(String name);


    @Query("select new com.hassan.pets.Records.ItemRecord" +
            "(i.itemId, i.name, i.price, i.description, i.stock,i.imageUrl, i.category) " +
            "from Items i  where i.itemId = :id")
    Optional<ItemRecord> findItemCategoryDetails(Long id);


    @Query("select new com.hassan.pets.Records.ItemRecord" +
            "(i.itemId, i.name, i.price, i.description, i.stock,i.imageUrl, i.category) from Items i")
    List<ItemRecord> findItemCategoryDetails();


    @Query("select new com.hassan.pets.Records.ItemRecord" +
            "(i.itemId, i.name, i.price, i.description, i.stock,i.imageUrl, i.category) from Items i")
    Page<ItemRecord> findItemCategoryDetails(Pageable pageable);
}
