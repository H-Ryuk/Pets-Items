package com.hassan.pets.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Scope("prototype")
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String address;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole role;


    @OneToMany(mappedBy = "users")
    private List<Orders> ordersList = new ArrayList<>();


    public Users(Long userId, String username, String password, String email, String address, String phoneNumber, UserRole role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }


    public enum UserRole {
        CUSTOMER, ADMIN;
    }

}
