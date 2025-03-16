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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LoyaltyTier getLoyaltyTier() {
        return loyaltyTier;
    }

    public void setLoyaltyTier(LoyaltyTier loyaltyTier) {
        this.loyaltyTier = loyaltyTier;
    }

    public List<CashbackOfferDTO> getEligibleOffers() {
        return eligibleOffers;
    }

    public void setEligibleOffers(List<CashbackOfferDTO> eligibleOffers) {
        this.eligibleOffers = eligibleOffers;
    }

    private String email;
    private LoyaltyTier loyaltyTier;
    private List<CashbackOfferDTO> eligibleOffers = new ArrayList<>();
}