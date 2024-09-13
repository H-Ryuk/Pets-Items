package com.hassan.pets.Repository;

import com.hassan.pets.Model.WishLists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishListRepo extends JpaRepository<WishLists,Long> {

    @Query(value = "select * from wish_lists where user_fk = :userId", nativeQuery = true)
    Optional<WishLists> findByUserId(@Param("userId") Long userId);


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "delete from wish_list_items where wish_fk = :wishlistId and item_fk = :itemId", nativeQuery = true)
    int removeFromWishlist(Long wishlistId, Long itemId);


    @Query(value = "select * from wish_lists wl join wish_list_items wli on wl.wishlist_id = wli.wish_fk " +
            " where wl.user_fk = :userId", nativeQuery = true)
    Optional<WishLists> getItemsFromWishListByUserId(@Param("userId") Long userId);


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "delete from wish_list_items where wish_fk = (select wishlist_id from wish_lists where user_fk = :userId)", nativeQuery = true)
    void clearWishListItemsByWishlistId(@Param("userId") Long userId);

}
