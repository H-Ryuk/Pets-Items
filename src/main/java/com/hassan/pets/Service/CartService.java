package com.hassan.pets.Service;

import com.hassan.pets.Exception.EmptyCartException;
import com.hassan.pets.Exception.RemovingItemFailedException;
import com.hassan.pets.Exception.TargetNotFoundException;
import com.hassan.pets.Model.Carts;
import com.hassan.pets.Model.Items;
import com.hassan.pets.Model.Users;
import com.hassan.pets.Repository.CartRepo;
import com.hassan.pets.Repository.ItemRepo;
import com.hassan.pets.Repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CartService {



    private final String targetName = "User";

    private final CartRepo cartRepo;
    private final UserRepo userRepo;

    public CartService(CartRepo cartRepo, UserRepo userRepo, ItemRepo itemRepo) {
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
    }


    public void addToCart(List<Items> itemsList, Users user) {
        Users existingUser = userRepo.findById(user.getUserId())
                .orElseThrow(() -> new TargetNotFoundException(targetName, user.getUserId()));

        cartRepo.findByUserId(existingUser.getUserId())
                .ifPresentOrElse(cart -> {
                            cart.getItemsList().addAll(itemsList);
                            cart.setItemsList(cart.getItemsList());
                            cartRepo.save(cart);
                        },
                        () -> {
                            Carts newCart = new Carts();
                            newCart.setUser(user);
                            newCart.setItemsList(itemsList);
                            cartRepo.save(newCart);
                        });
    }



    public void removeItemFromCart(Long itemId, Long cartId) {
        int row = cartRepo.removeItemFromCart(itemId, cartId);
        if(row == 0){
            throw new RemovingItemFailedException(itemId, cartId);
        }
    }



    public void clearCartAfterOrder(Long cartId) {
        cartRepo.clearCartAfterOrder(cartId);
    }



    public List<Items> getAllItemsFromCartByUserId(Long userId){
        return cartRepo.findItemsAndUserByUserId(userId)
                .map(Carts::getItemsList)
                .orElseThrow(() -> new EmptyCartException(userId));
    }

}



