package com.teamtwo.stocko_supply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockoSupply {

    public static void main(String[] args) {
        SpringApplication.run(StockoSupply.class, args);
        System.out.println("started in http://localhost:8080/");
    }

}
