package com.teamtwo.stocko_supply.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamtwo.stocko_supply.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);
}
