package com.store.cashback.service;

import com.store.cashback.dto.CashbackOfferDTO;
import com.store.cashback.dto.UserEligibilityDTO;
import com.store.cashback.model.*;
import com.store.cashback.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CashbackEligibilityService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CashbackOfferRepository offerRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    /**
     * Find all users eligible for any active cashback offers.
     */
    public List<UserEligibilityDTO> findEligibleUsers() {
        // Get active users
        List<User> activeUsers = userRepository.findByAccountActiveTrue();

        // Get active offers
        List<CashBackOffer> activeOffers = offerRepository.findByActiveTrue();

        // Calculate cutoff date for recent purchases (3 months ago)
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);

        // For each user, check eligibility for each offer
        List<UserEligibilityDTO> eligibleUsers = new ArrayList<>();

        for (User user : activeUsers) {
            List<CashbackOfferDTO> eligibleOffers = new ArrayList<>();

            // Check each offer for eligibility
            for (CashBackOffer offer : activeOffers) {
                if (isUserEligibleForOffer(user, offer, threeMonthsAgo)) {
                    eligibleOffers.add(convertToDTO(offer));
                }
            }

            // If user is eligible for any offers, add to results
            if (!eligibleOffers.isEmpty()) {
                UserEligibilityDTO userDTO = new UserEligibilityDTO();
                userDTO.setUserId(user.getId());
                userDTO.setUsername(user.getUsername());
                userDTO.setEmail(user.getEmail());
                userDTO.setLoyaltyTier(user.getLoyaltyTier());
                userDTO.setEligibleOffers(eligibleOffers);

                eligibleUsers.add(userDTO);
            }
        }

        return eligibleUsers;
    }

    /**
     * Determine if a specific user is eligible for a specific offer.
     */
    private boolean isUserEligibleForOffer(User user, CashBackOffer offer, LocalDateTime threeMonthsAgo) {
        // Check if offer is active and not expired
        if (!offer.isActive() || offer.getEndDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        // Check if user has already claimed this offer
        if (user.getClaimedOffers().contains(offer)) {
            return false;
        }

        // Check loyalty tier eligibility
        if (!offer.getEligibleTiers().isEmpty() && !offer.getEligibleTiers().contains(user.getLoyaltyTier())) {
            return false;
        }

        // Check if user has made sufficient purchases
        Double totalPurchases = purchaseRepository.sumPurchaseAmountByUserIdAndPurchaseDateAfter(
                user.getId(), threeMonthsAgo);

        if (totalPurchases == null || totalPurchases < offer.getMinimumPurchaseAmount()) {
            return false;
        }

        // Check for purchase category requirements if any
        if (!offer.getEligibleCategories().isEmpty()) {
            boolean hasPurchaseInEligibleCategory = user.getPurchases().stream()
                    .filter(p -> p.getPurchaseDate().isAfter(threeMonthsAgo))
                    .anyMatch(p -> offer.getEligibleCategories().contains(p.getCategory()));

            if (!hasPurchaseInEligibleCategory) {
                return false;
            }
        }

        // User is eligible for this offer
        return true;
    }

    /**
     * Convert a CashbackOffer entity to its DTO representation.
     */
    private CashbackOfferDTO convertToDTO(CashBackOffer offer) {
        CashbackOfferDTO dto = new CashbackOfferDTO();
        dto.wait(offer.getId());
        dto.setName(offer.getName());
        dto.setDescription(offer.getDescription());
        dto.setCashbackPercentage(offer.getCashbackPercentage());
        dto.setEndDate(offer.getEndDate());
        return dto;
    }
}