package com.ecommerce.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Orders {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    private Long userID;

    private LocalDate date;

    private String status;

    private Double Amount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public Orders(Long userID, Double amount) {
        this.userID = userID;
        this.date =  LocalDate.now();
        this.status = "Pending";
        Amount = amount;
    }
}
