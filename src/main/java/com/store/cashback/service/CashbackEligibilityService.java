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

/**
 * Service responsible for determining which users are eligible for which cashback offers.
 * This service implements business rules for cashback eligibility including:
 * - User account status
 * - Purchase history requirements
 * - Loyalty tier requirements
 * - Purchase category requirements
 */
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
     *
     * @return List of eligible users with their matching offers
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
            // Keep track of offers this user is eligible for
            List<CashbackOfferDTO> eligibleOffers = new ArrayList<>();

            // Check each offer for eligibility
            for (CashBackOffer offer : activeOffers) {
                // If user is eligible for this offer, add it to their list
                if (isUserEligibleForOffer(user, offer, threeMonthsAgo)) {
                    eligibleOffers.add(convertToDTO(offer));
                }
            }

            // If user is eligible for any offers, create a DTO and add to results
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
     *
     * @param user The user to check eligibility for
     * @param offer The cashback offer to check against
     * @param threeMonthsAgo The cutoff date for recent purchases
     * @return true if the user is eligible for the offer, false otherwise
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

        // If totalPurchases is null (no purchases) or below the minimum, user is not eligible
        if (totalPurchases == null || totalPurchases < offer.getMinimumPurchaseAmount()) {
            return false;
        }

        // Check for purchase category requirements if any
        if (!offer.getEligibleCategories().isEmpty()) {
            // Traditional approach instead of streams
            boolean hasPurchaseInEligibleCategory = false;

            // Loop through all the user's purchases
            for (Purchase purchase : user.getPurchases()) {
                // Only consider recent purchases (within last 3 months)
                if (purchase.getPurchaseDate().isAfter(threeMonthsAgo)) {
                    // Check if this purchase's category is eligible for the offer
                    if (offer.getEligibleCategories().contains(purchase.getCategory())) {
                        // Found an eligible purchase, no need to check further
                        hasPurchaseInEligibleCategory = true;
                        break;
                    }
                }
            }

            // If no eligible purchase was found, user doesn't qualify
            if (!hasPurchaseInEligibleCategory) {
                return false;
            }
        }

        // If we've passed all checks, user is eligible for this offer
        return true;
    }

    /**
     * Convert a CashbackOffer entity to its DTO representation.
     *
     * @param offer The entity to convert
     * @return The equivalent DTO object
     */
    private CashbackOfferDTO convertToDTO(CashBackOffer offer) {
        CashbackOfferDTO dto = new CashbackOfferDTO();
        dto.setId(offer.getId());  // Fixed from dto.wait(offer.getId())
        dto.setName(offer.getName());
        dto.setDescription(offer.getDescription());
        dto.setCashbackPercentage(offer.getCashbackPercentage());
        dto.setEndDate(offer.getEndDate());
        return dto;
    }
}