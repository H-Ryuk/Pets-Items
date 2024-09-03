package com.hassan.pets.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Component
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String username;
    private String password;
    private String email;
    private String address;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole role;



    @OneToMany(mappedBy = "users")
    private List<Orders> ordersList = new ArrayList<>();





    public enum UserRole {
        CUSTOMER,ADMIN;
    }

}
