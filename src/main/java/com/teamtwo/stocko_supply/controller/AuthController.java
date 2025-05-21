package com.teamtwo.stocko_supply.controller;

import com.teamtwo.stocko_supply.models.User;
import com.teamtwo.stocko_supply.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String loginForm(HttpServletRequest request, Model model) {
        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("currentUser") != null) {
            String username = (String) session.getAttribute("username");
            User user = userRepository.findByUsername(username);
            model.addAttribute("currentUser", user);
            return "redirect:/dashboard";
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

        User user = userService.login(username, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user);
            System.out.println("success");
            return "redirect:/dashboard";
        }

        model.addAttribute("error", "Username atau password salah!");
        return "auth/login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        session.invalidate();
        redirectAttributes.addFlashAttribute("success", "Anda berhasil logout");

        return "redirect:/auth/login";
    }

    @GetMapping("/register")
    public String registerForm(HttpServletRequest request) {
        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedInUser") != null) {
            return "redirect:/dashboard";
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = true) String role,
            HttpServletRequest request,
            Model model,
            RedirectAttributes redirectAttributes) {
        // Validate input
        if (username == null || username.isEmpty() || username.length() < 3) {
            model.addAttribute("error", "Username harus minimal 3 karakter");
            return "auth/register";
        }

        if (password == null || password.isEmpty() || password.length() < 8) {
            model.addAttribute("error", "Password harus minimal 8 karakter");
            return "auth/register";
        }

        HttpSession session = request.getSession(false);
        String currentUsername = session != null ? (String) session.getAttribute("loggedInUser") : null;
        String currentRole = session != null ? (String) session.getAttribute("userRole") : null;

        String finalRole;

        if ("admin".equalsIgnoreCase(role)) {
            if ("admin".equalsIgnoreCase(currentUsername) && "admin".equalsIgnoreCase(currentRole)) {
                finalRole = "admin";
            } else {
                model.addAttribute("error", "Hanya admin yang dapat membuat user dengan role admin");
                return "redirect:/auth/register";
            }
        } else {
            finalRole = "user";
        }

        if (userService.addUser(username, password, finalRole)) {
            redirectAttributes.addFlashAttribute("success", "Registrasi berhasil!");
            return "redirect:/dashboard/admin";
        }

        model.addAttribute("error", "Username sudah digunakan. Pilih username lain.");
        return "auth/register";
    }
}
