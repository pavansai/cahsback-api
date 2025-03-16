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
}