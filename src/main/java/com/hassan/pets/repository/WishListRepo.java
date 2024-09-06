package com.hassan.pets.repository;

import com.hassan.pets.model.WishLists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishListRepo extends JpaRepository<WishLists,Long> {

    @Query(value = "select * from wish_lists where user_fk = :userId", nativeQuery = true)
    WishLists findByUserId(@Param("userId") Long userId);


    @Modifying
    @Query(value = "delete from wish_list_items where wish_fk = :wishlistId and item_fk = :itemId", nativeQuery = true)
    void removeFromWishlist(Long wishlistId, Long itemId);


}
