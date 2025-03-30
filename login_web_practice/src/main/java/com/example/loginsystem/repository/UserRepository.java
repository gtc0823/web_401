package com.example.loginsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.loginsystem.model.NormalUser;

@Repository
public interface UserRepository extends JpaRepository<NormalUser, Long> {
    NormalUser findByUsernameAndPassword(String username, String password);
    NormalUser findByUsername(String username);
}
