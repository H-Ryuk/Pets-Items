package com.hassan.pets.service;

import com.hassan.pets.DTO.OrderRecord;
import com.hassan.pets.DTO.UserOrderRecord;
import com.hassan.pets.DTO.UserRecord;
import com.hassan.pets.model.Users;
import com.hassan.pets.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    private final UserRepo userRepo;


    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    public Users addUser(Users user) {
        return userRepo.save(user);
    }


    public List<UserRecord> getAll() {
        return userRepo.findAll().stream()
                .map(users -> new UserRecord(
                        users.getUserId(),
                        users.getUsername(),
                        users.getPassword(),
                        users.getEmail(),
                        users.getAddress(),
                        users.getPhoneNumber()
                ))
                .toList();
    }


    public UserOrderRecord getUserOrdersById(Long userId) {
        return userRepo.findById(userId)
                .map(users -> {
                    List<OrderRecord> orderRecords = users.getOrdersList().stream()
                            .map(orders -> new OrderRecord(
                                    orders.getOrderId(),
                                    orders.getOrderDate(),
                                    orders.getStatus(),
                                    orders.getTotalAmount()
                            )).toList();

                    return new UserOrderRecord(
                            users.getUsername(),
                            users.getAddress(),
                            users.getPhoneNumber(),
                            orderRecords
                    );
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }




    public void updateUser(Users user) {
        userRepo.findById(user.getUserId())
                .ifPresent(users -> {
                    users.setAddress(user.getAddress());
                    users.setEmail(user.getEmail());
                    users.setPassword(user.getPassword());
                    users.setPhoneNumber(user.getPhoneNumber());
                    users.setRole(user.getRole());
                    users.setUsername(user.getUsername());

                    userRepo.save(users);
                });
    }




    public Users deleteUser(Users user) {
        userRepo.delete(user);
        return user;
    }


}
