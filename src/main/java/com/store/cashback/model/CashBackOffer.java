package com.store.cashback.model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class CashBackOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double cashbackPercentage;
    private double minimumPurchaseAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<LoyaltyTier> eligibleTiers = new ArrayList<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<PurchaseCategory> eligibleCategories = new ArrayList<>();

    private boolean active;

}