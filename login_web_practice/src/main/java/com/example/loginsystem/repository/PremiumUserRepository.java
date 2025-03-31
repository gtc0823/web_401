package com.example.loginsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.loginsystem.model.PremiumUser;

@Repository
public interface PremiumUserRepository extends JpaRepository<PremiumUser, Integer> {
    PremiumUser findByUsernameAndPassword(String username, String password);
    PremiumUser findByUsername(String username);
} 