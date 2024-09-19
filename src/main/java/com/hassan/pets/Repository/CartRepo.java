package com.hassan.pets.Repository;

import com.hassan.pets.Model.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Carts,Long> {

    @Query(value = "select * from carts where user_fk = :userId", nativeQuery = true)
    Optional<Carts> findByUserId(Long userId);



    @Query(value = "select * from carts c join carts_items i " +
            "on c.cart_id like i.cart_fk where c.user_fk = :userId", nativeQuery = true)
    Optional<Carts> findItemsAndUserByUserId(Long userId);




    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "delete from carts_items where item_fk = :itemId and cart_fk = :cartId", nativeQuery=true)
    int removeItemFromCart(Long itemId, Long cartId);



    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "delete from carts_items where cart_fk = (select cart_id from carts where user_fk = :userId)", nativeQuery = true)
    void clearCartAfterOrder(Long userId);




}
