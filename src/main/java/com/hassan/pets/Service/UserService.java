package com.hassan.pets.Service;

import com.hassan.pets.Config.BCrypt;
import com.hassan.pets.Records.OrderRecord;
import com.hassan.pets.Records.UserOrderRecord;
import com.hassan.pets.Records.UserRecord;
import com.hassan.pets.Exception.TargetNotFoundException;
import com.hassan.pets.Model.Users;
import com.hassan.pets.Repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class UserService {


    private final String targetName = "User";
    private final UserRepo userRepo;


    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    public Users addUser(UserRecord userRecord) {
        Users user = convertUserRecordToUser(userRecord);
        user.setPassword(BCrypt.encoder().encode(user.getPassword()));
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
                        users.getPhoneNumber(),
                        users.getRole()
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
                .orElseThrow(() -> new TargetNotFoundException(targetName, userId));
    }


    public void updateUser(UserRecord userRecord) {
        Users user = convertUserRecordToUser(userRecord);

        userRepo.findById(user.getUserId())
                .ifPresentOrElse(users -> {
                    users.setAddress(user.getAddress());
                    users.setEmail(user.getEmail());
                    users.setPassword(user.getPassword());
                    users.setPhoneNumber(user.getPhoneNumber());
                    users.setRole(user.getRole());
                    users.setUsername(user.getUsername());

                    userRepo.save(users);

                }, () -> {
                    throw new TargetNotFoundException(targetName, user.getUserId());
                });
    }


    public void deleteUser(Long userId) {

        int row = userRepo.deleteUserById(userId);
        if(row == 0){
            throw new TargetNotFoundException(targetName, userId);
        }
    }



    public Users convertUserRecordToUser(UserRecord userRecord) {
        return new Users(
                userRecord.userId(),
                userRecord.username(),
                userRecord.password(),
                userRecord.email(),
                userRecord.address(),
                userRecord.phoneNumber(),
                userRecord.role()
        );
    }


}
