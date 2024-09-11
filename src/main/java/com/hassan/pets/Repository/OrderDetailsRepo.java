package com.hassan.pets.Repository;

import com.hassan.pets.Model.OrderDetails;
import com.hassan.pets.Model.Orders;
import com.hassan.pets.Records.OrderDetailsRecord;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long> {

    @Query("select new com.hassan.pets.Model.OrderDetails" +
            "(od.detailOrderId, od.quantity, od.price, od.item, od.orders) from OrderDetails od")
    List<OrderDetails> getOrderDetails();

    @Query(value = "select * from order_details od where od.order_fk = :orderId and od.item_fk = :itemId;", nativeQuery = true)
    Optional<OrderDetails> checkItemExisting(@Param("orderId") Long orderId, @Param("itemId") Long itemId);

    @Query("select new com.hassan.pets.Model.OrderDetails" +
            "(od.detailOrderId, od.quantity, od.price, od.item, od.orders) from OrderDetails od where od.orders = :order")
    List<OrderDetails> getOrderDetailsByOrderId(@Param("order") Orders order);
}
