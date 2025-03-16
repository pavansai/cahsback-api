// Create CashbackOfferRepository.java in repository package
package com.store.cashback.repository;

import com.store.cashback.model.CashBackOffer;
import com.store.cashback.model.LoyaltyTier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CashbackOfferRepository extends JpaRepository<CashBackOffer, Long> {
    // Find active offers
    List<CashBackOffer> findByActiveTrue();

    // Find offers eligible for a specific loyalty tier
    List<CashBackOffer> findByActiveTrueAndEligibleTiersContaining(LoyaltyTier tier);
}