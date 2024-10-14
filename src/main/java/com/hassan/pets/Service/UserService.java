package com.hassan.pets.Service;

import com.hassan.pets.Records.UserLogIn;
import com.hassan.pets.Security.Encryption.BCrypt;
import com.hassan.pets.Records.OrderRecord;
import com.hassan.pets.Records.UserOrderRecord;
import com.hassan.pets.Records.UserRecord;
import com.hassan.pets.Exception.TargetNotFoundException;
import com.hassan.pets.Model.Users;
import com.hassan.pets.Repository.UserRepo;
import com.hassan.pets.Security.Jwt.JwtConfig;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {


    private final String targetName = "User";
    private final UserRepo userRepo;
    private final BCrypt bCrypt;
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;


    public UserService(UserRepo userRepo, BCrypt bCrypt, AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
        this.userRepo = userRepo;
        this.bCrypt = bCrypt;
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
    }


    public Users register(UserRecord userRecord) {
        Users user = convertUserRecordToUser(userRecord);
        user.setPassword(bCrypt.encoder().encode(user.getPassword()));
        return userRepo.save(user);
    }


    public List<UserRecord> getAll() {
        return userRepo.findAll().stream()
                .map(users -> new UserRecord(
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
                userRecord.username(),
                userRecord.password(),
                userRecord.email(),
                userRecord.address(),
                userRecord.phoneNumber(),
                userRecord.role()
        );
    }


    public String login(UserLogIn user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.email(), user.password()));

        if (authentication.isAuthenticated())
            return jwtConfig.generateToken(user.email());
        else
            return "LogIn Failed";
    }
}
