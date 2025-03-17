package com.store.cashback.controller;

import com.store.cashback.dto.UserEligibilityDTO;
import com.store.cashback.service.CashbackEligibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cashback")
public class CashbackController {

    @Autowired
    private CashbackEligibilityService cashbackEligibilityService;

    /**
     * Get all users eligible for cashback offers
     * @return List of users with their eligible offers
     */
    @GetMapping("/eligible-users")
    public ResponseEntity<List<UserEligibilityDTO>> getEligibleUsers() {
        List<UserEligibilityDTO> eligibleUsers = cashbackEligibilityService.findEligibleUsers();
        return ResponseEntity.ok(eligibleUsers);
    }
}