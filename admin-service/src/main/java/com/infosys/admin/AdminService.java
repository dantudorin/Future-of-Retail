package com.infosys.admin;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.SpringApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EntityScan(basePackages = "com.infosys.admin.model")
@EnableJpaRepositories(basePackages = "com.infosys.admin.repository")
public class AdminService {
    public static void main(String[] args) {

        SpringApplication.run(AdminService.class, args);
    }
}
