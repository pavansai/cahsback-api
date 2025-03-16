// Create UserEligibilityDTO.java in dto package
package com.store.cashback.dto;

import com.store.cashback.model.LoyaltyTier;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserEligibilityDTO {
    private Long userId;
    private String username;
    private String email;
    private LoyaltyTier loyaltyTier;
    private List<CashbackOfferDTO> eligibleOffers = new ArrayList<>();
}