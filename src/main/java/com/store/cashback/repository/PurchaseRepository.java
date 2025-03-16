// Create PurchaseRepository.java in repository package
package com.store.cashback.repository;

import com.store.cashback.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    // Find total purchases by user in the last 3 months
    @Query("SELECT SUM(p.amount) FROM Purchase p WHERE p.user.id = :userId AND p.purchaseDate >= :threeMonthsAgo")
    Double sumPurchaseAmountByUserIdAndPurchaseDateAfter(@Param("userId") Long userId, @Param("threeMonthsAgo") LocalDateTime threeMonthsAgo);
}