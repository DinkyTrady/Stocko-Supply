package com.teamtwo.stocko_supply.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamtwo.stocko_supply.models.User;
import com.teamtwo.stocko_supply.repository.UserRepository;

@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean addUser(String username, String password, String role) {
        if (users.containsKey(username)) {
            return false;
        }

        users.put(username, new User(username, password, role));
        return true;
    }

    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
