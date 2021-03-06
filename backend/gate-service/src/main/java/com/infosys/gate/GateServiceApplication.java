package com.infosys.gate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@EnableJpaRepositories(basePackages = {"com.infosys.gate.repository", "com.infosys.admin.repository"})
@EntityScan(basePackages = {"com.infosys.gate.model", "com.infosys.admin.model"})
@ComponentScan(basePackages = {"com.infosys.gate"})
@ComponentScan(basePackages = {"com.infosys.admin.config"},
		excludeFilters = {@ComponentScan.Filter(
				type = FilterType.ASSIGNABLE_TYPE
			)
		})
public class GateServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GateServiceApplication.class, args);
	}
}
