package com.hassan.pets.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Component
public class Carts implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;


    @OneToOne
    @JoinColumn(name = "user_fk")
    private Users user;


    @ManyToMany
    @JoinTable(
            name = "CartsItems",
            joinColumns = @JoinColumn(name = "cart_fk"),
            inverseJoinColumns = @JoinColumn(name = "item_fk")
    )
    private List<Items> itemsList = new ArrayList<>();


}
