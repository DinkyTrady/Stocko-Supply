package com.teamtwo.stocko_supply.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class HomeController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
