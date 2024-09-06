package com.hassan.pets.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.math.BigDecimal;



@Data
@Entity
@Component
public class Items implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String name;
    @Column(columnDefinition = "text")
    private String description;
    private BigDecimal price;
    private int stock;

    @Lob
    private byte[] imageUrl;


    @ManyToOne
    @JoinColumn(name = "category_fk")
    private Categories category;


}
