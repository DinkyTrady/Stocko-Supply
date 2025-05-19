package com.example.warehouse.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.warehouse.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String loginForm(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedInUser") != null) {
            return "redirect:/";
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request,
            Model model) {
        if (username == null || username.isEmpty()) {
            model.addAttribute("error", "Username perlu diisi!");
            return "auth/login";
        }

        if (password == null || username.isEmpty()) {
            model.addAttribute("error", "Username perlu diisi!");
            return "auth/login";
        }

        if (userService.login(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", username);
            return "redirect:/";
        }

        model.addAttribute("error", "Username atau Password salah!");
        return "redirect:/";
    }
}
