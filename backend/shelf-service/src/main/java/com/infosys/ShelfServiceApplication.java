package com.infosys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.infosys.config"})
public class ShelfServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShelfServiceApplication.class, args);
    }
}
