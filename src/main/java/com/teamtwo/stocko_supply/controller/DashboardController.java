package com.teamtwo.stocko_supply.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.teamtwo.stocko_supply.models.User;
import com.teamtwo.stocko_supply.repository.UserRepository;
import com.teamtwo.stocko_supply.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String showDashboard(Model model, HttpServletRequest request) {
        try {
            // First check if user is authenticated
            User currentUser = userService.getCurrentUser(request);
            if (currentUser == null) {
                throw new Error("currentUser is null");
            }

            boolean isAdmin = currentUser != null && "admin".equalsIgnoreCase(currentUser.getUsername())
                    && "admin".equalsIgnoreCase(currentUser.getRole());
            List<User> users = userRepository.findAllByOrderByIdAsc();

            model.addAttribute("users", users);
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("currentUser", currentUser);

            return "dashboard/index";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/auth/login"; // Redirect to login if any error occurs
        }
    }
}
