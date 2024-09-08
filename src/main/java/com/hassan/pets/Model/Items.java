package com.hassan.pets.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.math.BigDecimal;



@Data
@AllArgsConstructor
@NoArgsConstructor
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
