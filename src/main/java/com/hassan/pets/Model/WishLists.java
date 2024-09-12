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
public class WishLists implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;


    @OneToOne(targetEntity = Users.class)
    @JoinColumn(name = "user_fk", referencedColumnName = "userId")
    private Users users;


    @ManyToMany
    @JoinTable(
            name = "wishList_Items",
            joinColumns = @JoinColumn(name = "wish_fk"),
            inverseJoinColumns = @JoinColumn(name = "item_fk")
    )
    private List<Items> itemsList = new ArrayList<>();

}
