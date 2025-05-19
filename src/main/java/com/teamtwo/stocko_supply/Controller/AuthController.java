package com.teamtwo.stocko_supply.controller;

import com.teamtwo.stocko_supply.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginForm(HttpServletRequest request, Model model) {
        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedInUser") != null) {
            return "redirect:/";
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request,
            Model model) {
        if (username == null || username.isEmpty()) {
            model.addAttribute("error", "Username tidak boleh kosong");
            return "auth/login";
        }

        if (password == null || password.isEmpty()) {
            model.addAttribute("error", "Password tidak boleh kosong");
            return "auth/login";
        }

        if (userService.login(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", username);
            return "redirect:/";
        }
        model.addAttribute("error", "Username atau password salah!");
        return "auth/login";
    }
}
