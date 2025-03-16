package com.store.cashback.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data

public class Purchase{
    @Id
    @GenertaedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;
    private LocalDateTime purchaseDate;
    private double amount;
    private String storeLocation;

    @Enumerated(EnumType.STRING)
    private PurchaseCategory category;
}