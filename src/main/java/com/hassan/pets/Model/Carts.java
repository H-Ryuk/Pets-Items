package com.hassan.pets.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
public class Carts implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;


    @OneToOne
    @JoinColumn(name = "user_fk")
    private Users user;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "CartsItems",
            joinColumns = @JoinColumn(name = "cart_fk"),
            inverseJoinColumns = @JoinColumn(name = "item_fk")
    )
    private List<Items> itemsList = new ArrayList<>();


}
