// User.java
package com.store.cashback.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private LocalDateTime registrationDate;
    private boolean accountActive;

    @Enumerated(EnumType.STRING)
    private LoyaltyTier loyaltyTier;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Purchase> purchases = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setAccountActive(boolean accountActive) {
        this.accountActive = accountActive;
    }

    public void setLoyaltyTier(LoyaltyTier loyaltyTier) {
        this.loyaltyTier = loyaltyTier;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public void setClaimedOffers(List<CashBackOffer> claimedOffers) {
        this.claimedOffers = claimedOffers;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public boolean isAccountActive() {
        return accountActive;
    }

    public LoyaltyTier getLoyaltyTier() {
        return loyaltyTier;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public List<CashBackOffer> getClaimedOffers() {
        return claimedOffers;
    }

    @ManyToMany
    @JoinTable(
            name = "users_claimed_offers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id")
    )
    private List<CashBackOffer> claimedOffers = new ArrayList<>();
}