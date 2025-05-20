package com.teamtwo.stocko_supply.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final Map<String, String> users = new HashMap<>();

    public boolean addUser(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }

        users.put(username, password);
        return true;
    }

    public boolean login(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}
