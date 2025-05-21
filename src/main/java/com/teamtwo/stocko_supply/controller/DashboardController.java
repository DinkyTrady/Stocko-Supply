package com.teamtwo.stocko_supply.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.teamtwo.stocko_supply.models.User;
import com.teamtwo.stocko_supply.repository.UserRepository;
import com.teamtwo.stocko_supply.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

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
            userService.prepareCurrentUser(request, model);
            User currentUser = userService.getCurrentUser(request);

            boolean isAdmin = currentUser != null && "admin".equalsIgnoreCase(currentUser.getUsername())
                    && "admin".equalsIgnoreCase(currentUser.getRole());
            List<User> users = userRepository.findAllByOrderByIdAsc();

            model.addAttribute("users", users);
            model.addAttribute("isAdmin", isAdmin);

            if (currentUser == null) {
                return "redirect:/auth/login";
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return "dashboard/index";
    }
}
