package com.teamtwo.stocko_supply.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.teamtwo.stocko_supply.models.User;
import com.teamtwo.stocko_supply.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean addUser(String username, String password, String role) {
        try {
            User userExist = userRepository.findByUsername(username);
            if (userExist != null) {
                System.out.println("ini error" + userExist);
                return false;
            }

            User user = new User(username, password, role);
            userRepository.save(user);

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public User login(String username, String password) {
        return Optional.ofNullable(userRepository.findByUsernameAndPassword(username, password)).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public boolean updateUser(Long id, String username, String password, String role) {
        User user = getUserById(id);
        if (user == null) {
            return false;
        }

        if (username != null && !username.isEmpty()) {
            user.setUsername(username);
        }

        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
        }
        user.setRole(role);
        userRepository.save(user);

        return true;
    }

    @Transactional
    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id.intValue());
        return true;
    }

    // Check current user
    public void prepareCurrentUser(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
    }

    public User getCurrentUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("currentUser");
    }

    public boolean isAdmin(HttpServletRequest request) {
        User currentUser = getCurrentUser(request);
        return currentUser != null && "admin".equals(currentUser.getRole());
    }
    // ended
}
