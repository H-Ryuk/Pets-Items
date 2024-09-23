package com.hassan.pets.Repository;

import com.hassan.pets.Model.Orders;
import com.hassan.pets.Records.DateStatusOrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepo extends JpaRepository<Orders,Long> {


    @Query("select new com.hassan.pets.Records.DateStatusOrderRecord(o.orderId, o.orderDate, o.status) " +
            "from Orders o")
    List<DateStatusOrderRecord> getOrderDate();


    @Modifying
    @Query("update Orders o set o.status = 'DELIVERED' where o.orderId = :orderId")
    void changeOrderStatus(@Param("orderId") Long orderId);


    @Modifying
    @Query("update Orders o set o.status = 'CANCELLED' where o.orderId = :orderId")
    void cancelOrder(@Param("orderId") Long orderId);

}
