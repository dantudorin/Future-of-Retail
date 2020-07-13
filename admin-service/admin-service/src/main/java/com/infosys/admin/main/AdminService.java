package com.infosys.admin.main;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
@EntityScan(basePackages = "com.infosys.admin.model")
@EnableJpaRepositories(basePackages = "com.infosys.admin.repository")
public class AdminService {
    public static void main(String[] args) {

        SpringApplication.run(AdminService.class, args);
    }
}
