package com.store.cashback.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCashbackPercentage() {
        return cashbackPercentage;
    }

    public void setCashbackPercentage(double cashbackPercentage) {
        this.cashbackPercentage = cashbackPercentage;
    }

    public double getMinimumPurchaseAmount() {
        return minimumPurchaseAmount;
    }

    public void setMinimumPurchaseAmount(double minimumPurchaseAmount) {
        this.minimumPurchaseAmount = minimumPurchaseAmount;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public List<LoyaltyTier> getEligibleTiers() {
        return eligibleTiers;
    }

    public void setEligibleTiers(List<LoyaltyTier> eligibleTiers) {
        this.eligibleTiers = eligibleTiers;
    }

    public List<PurchaseCategory> getEligibleCategories() {
        return eligibleCategories;
    }

    public void setEligibleCategories(List<PurchaseCategory> eligibleCategories) {
        this.eligibleCategories = eligibleCategories;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}