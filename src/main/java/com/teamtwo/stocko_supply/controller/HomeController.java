package com.teamtwo.stocko_supply.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.teamtwo.stocko_supply.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
class HomeController {
    @Autowired
    private UserService userService;

    // @Autowired
    // private BarangService barangService;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        userService.prepareCurrentUser(request, model);

        return "dashboard/index";
    }
}
