package com.store.cashback.repository;

import com.store.cashback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Find users with active accounts
    List<User> findByAccountActiveTrue();

    // Find users who made purchases above a certain amount in the last month
    @Query("SELECT DISTINCT u FROM User u JOIN u.purchases p WHERE p.amount >= :amount AND p.purchaseDate >= :oneMonthAgo")
    List<User> findUsersWithPurchasesAboveAmountInLastMonth(@Param("amount") double amount, @Param("oneMonthAgo") LocalDateTime oneMonthAgo);
}