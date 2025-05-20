package com.teamtwo.stocko_supply.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.teamtwo.stocko_supply.service.UserService;

@Controller
class HomeController {
    @Autowired
    private UserService userService;

    // @Autowired
    // private BarangService barangService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
