package com.hassan.pets.Contoller;

import com.hassan.pets.DTO.UserOrderRecord;
import com.hassan.pets.DTO.UserRecord;
import com.hassan.pets.model.Users;
import com.hassan.pets.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public Users addUser(@RequestBody Users user){
        return userService.addUser(user);
    }


    @GetMapping
    public List<UserRecord> getAll(){
        return userService.getAll();
    }


    @GetMapping("{userId}")
    public UserOrderRecord getById(@PathVariable int userId){
        return userService.getById(userId);
    }


    @PutMapping
    public void updateUser(@RequestBody Users user){
        userService.updateUser(user);
    }


    @DeleteMapping
    public Users deleteUser(@RequestBody Users user){
        return userService.deleteUser(user);
    }

}
