package com.teamtwo.stocko_supply.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamtwo.stocko_supply.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    User findById(Long id);

    boolean existsById(Long id);

    List<User> findAllByOrderByIdAsc(); // urut berdasarkan ID naik

    List<User> findAllByOrderByIdDesc(); // urut berdasarkan ID turun (opsional)
}
