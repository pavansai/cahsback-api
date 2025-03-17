package com.store.cashback.controller;

import com.store.cashback.model.*;
import com.store.cashback.repository.CashbackOfferRepository;
import com.store.cashback.repository.PurchaseRepository;
import com.store.cashback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Initialize test data for the application
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CashbackOfferRepository offerRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public void run(String... args) {
        // Create users
        User user1 = createUser("john_doe", "john@example.com", LoyaltyTier.GOLD);
        User user2 = createUser("jane_smith", "jane@example.com", LoyaltyTier.SILVER);
        User user3 = createUser("mike_jones", "mike@example.com", LoyaltyTier.PLATINUM);
        User user4 = createUser("sarah_williams", "sarah@example.com", LoyaltyTier.BRONZE);

        // Create offers
        CashBackOffer groceryOffer = createOffer(
                "Grocery Cashback",
                "10% cashback on grocery purchases",
                10.0,
                50.0,
                List.of(LoyaltyTier.SILVER, LoyaltyTier.GOLD, LoyaltyTier.PLATINUM),
                List.of(PurchaseCategory.GROCERIES)
        );

        CashBackOffer electronicsOffer = createOffer(
                "Electronics Cashback",
                "5% cashback on electronics purchases",
                5.0,
                200.0,
                List.of(LoyaltyTier.GOLD, LoyaltyTier.PLATINUM),
                List.of(PurchaseCategory.ELECTRONICS)
        );

        CashBackOffer platinumOffer = createOffer(
                "Platinum Exclusive",
                "15% cashback on all purchases for platinum members",
                15.0,
                100.0,
                List.of(LoyaltyTier.PLATINUM),
                List.of(PurchaseCategory.GROCERIES, PurchaseCategory.ELECTRONICS, 
                      PurchaseCategory.CLOTHING, PurchaseCategory.HOME_GOODS)
        );

        // Create purchases
        createPurchase(user1, 120.0, PurchaseCategory.GROCERIES, "Store #123");
        createPurchase(user1, 350.0, PurchaseCategory.ELECTRONICS, "Store #456");
        createPurchase(user2, 75.0, PurchaseCategory.GROCERIES, "Store #789");
        createPurchase(user3, 500.0, PurchaseCategory.ELECTRONICS, "Store #456");
        createPurchase(user3, 200.0, PurchaseCategory.CLOTHING, "Store #101");
        createPurchase(user4, 30.0, PurchaseCategory.GROCERIES, "Store #123");
    }

    private User createUser(String username, String email, LoyaltyTier tier) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setRegistrationDate(LocalDateTime.now().minusMonths(6));
        user.setAccountActive(true);
        user.setLoyaltyTier(tier);
        return userRepository.save(user);
    }

    private CashBackOffer createOffer(
            String name,
            String description,
            double percentage,
            double minimumAmount,
            List<LoyaltyTier> tiers,
            List<PurchaseCategory> categories) {
        
        CashBackOffer offer = new CashBackOffer();
        offer.setName(name);
        offer.setDescription(description);
        offer.setCashbackPercentage(percentage);
        offer.setMinimumPurchaseAmount(minimumAmount);
        offer.setStartDate(LocalDateTime.now().minusDays(30));
        offer.setEndDate(LocalDateTime.now().plusDays(60));
        offer.setEligibleTiers(tiers);
        offer.setEligibleCategories(categories);
        offer.setActive(true);
        return offerRepository.save(offer);
    }

    private Purchase createPurchase(User user, double amount, PurchaseCategory category, String location) {
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setAmount(amount);
        purchase.setCategory(category);
        purchase.setStoreLocation(location);
        purchase.setPurchaseDate(LocalDateTime.now().minusDays(15));
        return purchaseRepository.save(purchase);
    }
}