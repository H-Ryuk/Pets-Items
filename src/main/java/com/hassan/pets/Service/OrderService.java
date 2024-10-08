package com.hassan.pets.Service;

import com.hassan.pets.Exception.EmptyCartException;
import com.hassan.pets.Exception.EmptyWishListException;
import com.hassan.pets.Exception.TargetNotFoundException;
import com.hassan.pets.Records.OrderDetailsRecord;
import com.hassan.pets.Records.UserAndItemsRecord;
import com.hassan.pets.Model.*;
import com.hassan.pets.Repository.CartRepo;
import com.hassan.pets.Repository.OrderDetailsRepo;
import com.hassan.pets.Repository.OrdersRepo;
import com.hassan.pets.Repository.WishListRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@Transactional
public class OrderService {


    private final String targetName = "User";
    private final OrdersRepo ordersRepo;
    private final OrderDetailsRepo orderDetailsRepo;
    private final CartRepo cartRepo;
    private final CartService cartService;
    private final WishListRepo wishListRepo;



    public OrderService(OrdersRepo ordersRepo, OrderDetailsRepo orderDetailsRepo, CartRepo cartRepo, CartService cartService, WishListRepo wishListRepo) {
        this.ordersRepo = ordersRepo;
        this.orderDetailsRepo = orderDetailsRepo;
        this.cartRepo = cartRepo;
        this.cartService = cartService;
        this.wishListRepo = wishListRepo;
    }



    public Orders addOrder(Users user) {
        Orders newOrder = new Orders();
        newOrder.setStatus(Orders.OrderStatus.PENDING);
        newOrder.setTotalAmount(null);
        newOrder.setUsers(user);
        return ordersRepo.save(newOrder);
    }



    public void addOrderDetails(String entity, Long userId) {

        UserAndItemsRecord userItems = getUserItemsRecord(entity, userId);

        if (entity.equals("cart")) {
            cartService.clearCartAfterOrder(userId);
        } else {
            wishListRepo.clearWishListItemsByWishlistId(userId);
        }


        Orders order = addOrder(userItems.user());  //// This method save order and return it
        for (Items item : userItems.itemsList()) {

            BigDecimal itemPrice = item.getPrice();
            if (itemPrice != null) {
                if (order.getTotalAmount() != null) {
                    order.setTotalAmount(order.getTotalAmount().add(itemPrice));
                } else {
                    order.setTotalAmount(itemPrice);
                }
            }

            orderDetailsRepo.checkItemExisting(order.getOrderId(), item.getItemId())
                    .ifPresentOrElse(orderDetails -> {
                        orderDetails.setQuantity(orderDetails.getQuantity() + 1);
                        orderDetails.setPrice(item.getPrice().multiply(BigDecimal.valueOf(orderDetails.getQuantity())));
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
    }




    private UserAndItemsRecord getUserItemsRecord(String entity, Long userId) {
        if (entity.equals("cart")) {
            return cartRepo.findItemsAndUserByUserId(userId)
                    .map(wishLists -> new UserAndItemsRecord(wishLists.getItemsList(), wishLists.getUser()))
                    .orElseThrow(() -> new EmptyCartException(userId));
        } else {
            return wishListRepo.getItemsFromWishListByUserId(userId)
                    .map(wishLists -> new UserAndItemsRecord(wishLists.getItemsList(), wishLists.getUsers()))
                    .orElseThrow(() -> new EmptyWishListException(userId));
        }
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




    public void cacelOrder(Long orderId) {
        ordersRepo.cancelOrder(orderId);
    }



}
