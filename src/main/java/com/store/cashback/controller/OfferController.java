package com.store.cashback.controller;

import com.store.cashback.model.CashBackOffer;
import com.store.cashback.model.LoyaltyTier;
import com.store.cashback.repository.CashbackOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    @Autowired
    private CashbackOfferRepository offerRepository;

    /**
     * Get all cashback offers
     * @return List of all offers
     */
    @GetMapping
    public ResponseEntity<List<CashBackOffer>> getAllOffers() {
        List<CashBackOffer> offers = offerRepository.findAll();
        return ResponseEntity.ok(offers);
    }

    /**
     * Get a specific offer by ID
     * @param id The offer ID
     * @return The offer if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<CashBackOffer> getOfferById(@PathVariable Long id) {
        Optional<CashBackOffer> offer = offerRepository.findById(id);
        return offer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get active offers
     * @return List of active offers
     */
    @GetMapping("/active")
    public ResponseEntity<List<CashBackOffer>> getActiveOffers() {
        List<CashBackOffer> activeOffers = offerRepository.findByActiveTrue();
        return ResponseEntity.ok(activeOffers);
    }

    /**
     * Get offers for a specific loyalty tier
     * @param tier The loyalty tier to filter by
     * @return List of offers for the specified tier
     */
    @GetMapping("/tier/{tier}")
    public ResponseEntity<List<CashBackOffer>> getOffersByTier(@PathVariable LoyaltyTier tier) {
        List<CashBackOffer> offers = offerRepository.findByActiveTrueAndEligibleTiersContaining(tier);
        return ResponseEntity.ok(offers);
    }
}