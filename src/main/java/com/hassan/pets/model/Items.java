package com.hassan.pets.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Component
public class Items implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;
    private String name;
    @Column(columnDefinition = "text")
    private String description;
    private BigDecimal price;
    private int stock;

    @Lob
    private byte[] imageUrl;



    @ManyToOne
    @JoinColumn(name = "categoryFK")
    private Categories category;



    @ManyToOne
    @JoinColumn(name = "wishListsFK")
    private WishLists wishLists;




}
