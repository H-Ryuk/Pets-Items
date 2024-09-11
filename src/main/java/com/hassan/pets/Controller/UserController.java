package com.hassan.pets.Controller;

import com.hassan.pets.Records.UserOrderRecord;
import com.hassan.pets.Records.UserRecord;
import com.hassan.pets.Model.Users;
import com.hassan.pets.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody @Valid UserRecord userRecord) {
        Users user = userService.addUser(userRecord);
        return new
                ResponseEntity<>("User " + user.getUsername() + " created successfully with ID: " + user.getUserId(),
                HttpStatus.CREATED);
    }


    @GetMapping
    public List<UserRecord> getAll() {
        return userService.getAll();
    }


    @GetMapping("{userId}")
    public ResponseEntity<?> getUserOrdersById(@PathVariable Long userId) {
        UserOrderRecord user = userService.getUserOrdersById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody Users user) {
        userService.updateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @DeleteMapping("{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        String status = userService.deleteUser(userId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }


}
