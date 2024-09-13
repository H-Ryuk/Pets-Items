package com.hassan.pets.Service;

import com.hassan.pets.Exception.RemovingItemFailedException;
import com.hassan.pets.Exception.TargetNotFoundException;
import com.hassan.pets.Model.Items;
import com.hassan.pets.Model.OrderDetails;
import com.hassan.pets.Model.Orders;
import com.hassan.pets.Records.ItemRecord;
import com.hassan.pets.Records.UserAndItemsRecord;
import com.hassan.pets.Model.WishLists;
import com.hassan.pets.Repository.OrderDetailsRepo;
import com.hassan.pets.Repository.WishListRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class WishListService {


    private final WishListRepo wishListRepo;
    private final OrderDetailsRepo orderDetailsRepo;
    private final OrderService orderService;

    public WishListService(WishListRepo wishListRepo, OrderDetailsRepo orderDetailsRepo, OrderService orderService) {
        this.wishListRepo = wishListRepo;
        this.orderDetailsRepo = orderDetailsRepo;
        this.orderService = orderService;
    }


    public void addItemToWishList(UserAndItemsRecord userAndItemsRecord) {
        wishListRepo.findByUserId(userAndItemsRecord.user().getUserId())
                .ifPresentOrElse(existingWishLists -> {
                            existingWishLists.getItemsList().addAll(userAndItemsRecord.itemsList());
                            existingWishLists.setItemsList(existingWishLists.getItemsList());
                            existingWishLists.setUsers(userAndItemsRecord.user());
                            wishListRepo.save(existingWishLists);
                        },
                        () -> {
                            WishLists newWishLists = new WishLists();
                            newWishLists.setItemsList(userAndItemsRecord.itemsList());
                            newWishLists.setUsers(userAndItemsRecord.user());
                            wishListRepo.save(newWishLists);
                        });

    }


    public void removeFromWishlist(Long wishlistId, Long itemId) {
        int row = wishListRepo.removeFromWishlist(wishlistId, itemId);
        if (row == 0) {
            throw new RemovingItemFailedException(itemId);
        }
    }


    public List<ItemRecord> getItemsFromWishListByUserId(Long userId) {
        return wishListRepo.getItemsFromWishListByUserId(userId)
                .orElseThrow(() -> new TargetNotFoundException("User", userId))
                .getItemsList()
                .stream()
                .map(w -> new ItemRecord(
                        w.getItemId(),
                        w.getName(),
                        w.getPrice(),
                        w.getDescription(),
                        w.getStock(),
                        w.getImageUrl(),
                        w.getCategory()
                )).toList();
    }




//    public void fromWishListToOrderDetails(String entity, Long userId) {
//
//        UserAndItemsRecord userItems = wishListRepo.getItemsFromWishListByUserId(userId)
//                .map(wishLists -> new UserAndItemsRecord(
//                        wishLists.getItemsList(),
//                        wishLists.getUsers()
//                ))
//                .orElseThrow(() -> new TargetNotFoundException("User", userId));
//        wishListRepo.clearWishListItemsByWishlistId(userId);
//
//
//        Orders order = orderService.addOrder(userItems.user());  //// This method save order and return it
//        for (Items item : userItems.itemsList()) {
//
//            if (order.getTotalAmount() != null)
//                order.setTotalAmount(item.getPrice().add(order.getTotalAmount()));
//            else
//                order.setTotalAmount(item.getPrice());
//
//            orderDetailsRepo.checkItemExisting(order.getOrderId(), item.getItemId())
//                    .ifPresentOrElse(orderDetails -> {
//                        orderDetails.setQuantity(orderDetails.getQuantity() + 1);
//                        orderDetailsRepo.save(orderDetails);
//                    }, () -> {
//                        OrderDetails od = new OrderDetails();
//                        od.setOrders(order);
//                        od.setQuantity(1);
//                        od.setItem(item);
//                        BigDecimal price = item.getPrice().multiply(BigDecimal.valueOf(od.getQuantity()));
//                        od.setPrice(price);
//                        orderDetailsRepo.save(od);
//                    });
//        }
//    }


}
