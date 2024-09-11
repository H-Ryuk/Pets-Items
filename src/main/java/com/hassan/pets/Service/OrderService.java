package com.hassan.pets.Service;

import com.hassan.pets.Exception.TargetNotFoundException;
import com.hassan.pets.Records.OrderDetailsRecord;
import com.hassan.pets.Records.userAndItemsRecord;
import com.hassan.pets.Model.*;
import com.hassan.pets.Repository.CartRepo;
import com.hassan.pets.Repository.OrderDetailsRepo;
import com.hassan.pets.Repository.OrdersRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class OrderService {


    private final String targetName = "User";
    private final OrdersRepo ordersRepo;
    private final OrderDetailsRepo orderDetailsRepo;
    private final CartRepo cartRepo;
    private final CartService cartService;

    public OrderService(OrdersRepo ordersRepo, OrderDetailsRepo orderDetailsRepo, CartRepo cartRepo, CartService cartService) {
        this.ordersRepo = ordersRepo;
        this.orderDetailsRepo = orderDetailsRepo;
        this.cartRepo = cartRepo;
        this.cartService = cartService;
    }


    public Orders addOrder(Users user) {
        Orders newOrder = new Orders();
        newOrder.setStatus(Orders.OrderStatus.PENDING);
        newOrder.setTotalAmount(null);
        newOrder.setUsers(user);
        return ordersRepo.save(newOrder);
    }


    public void addOrderDetails(Long userId) {

        userAndItemsRecord userItems = cartRepo.findItemsAndUserByUserId(userId)
                .map(carts -> new userAndItemsRecord(
                        carts.getItemsList(),
                        carts.getUser()
                ))
                .orElseThrow(() -> new TargetNotFoundException(targetName, userId));

        Orders order = addOrder(userItems.user());  //// This method save order and return it
        for (Items item : userItems.itemsList()) {

            if (order.getTotalAmount() != null)
                order.setTotalAmount(item.getPrice().add(order.getTotalAmount()));
            else
                order.setTotalAmount(item.getPrice());

            orderDetailsRepo.checkItemExisting(order.getOrderId(), item.getItemId())
                    .ifPresentOrElse(orderDetails -> {
                        orderDetails.setQuantity(orderDetails.getQuantity() + 1);
                        orderDetailsRepo.save(orderDetails);
                    }, () -> {
                        OrderDetails od = new OrderDetails();
                        od.setOrders(order);
                        od.setQuantity(1);
                        od.setItem(item);
                        BigDecimal price = item.getPrice().multiply(BigDecimal.valueOf(od.getQuantity()));
                        od.setPrice(price);
                        orderDetailsRepo.save(od);
                    });
        }
        cartService.clearCartAfterOrder(userId);
    }


    public List<OrderDetailsRecord> getOrderDetails() {
        return orderDetailsRepo.getOrderDetails().stream()
                .map(orderDetails -> new OrderDetailsRecord(
                        orderDetails.getDetailOrderId(),
                        orderDetails.getQuantity(),
                        orderDetails.getPrice(),
                        orderDetails.getItem().getItemId(),
                        orderDetails.getItem().getName(),
                        orderDetails.getItem().getPrice(),
                        orderDetails.getItem().getCategory().getName()
                )).toList();
    }


    public List<OrderDetailsRecord> getOrderDetailsByOrderId(Long orderId) {
        return ordersRepo.findById(orderId)
                .map(orderDetailsRepo::getOrderDetailsByOrderId)
                .orElseThrow(() -> new TargetNotFoundException(targetName, orderId))
                .stream()
                .map(o -> new OrderDetailsRecord(
                        o.getDetailOrderId(),
                        o.getQuantity(),
                        o.getPrice(),
                        o.getItem().getItemId(),
                        o.getItem().getName(),
                        o.getItem().getPrice(),
                        o.getItem().getCategory().getName()
                )).toList();
    }


}
