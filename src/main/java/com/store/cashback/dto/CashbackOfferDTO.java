package com.store.cashback.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CashbackOfferDTO {
    private Long id;
    private String name;
    private String description;
    private double cashbackPercentage;
    private LocalDateTime endDate;

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

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}