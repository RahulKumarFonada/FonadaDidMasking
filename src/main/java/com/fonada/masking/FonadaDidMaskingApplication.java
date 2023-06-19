package com.fonada.masking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAutoConfiguration
@EnableAsync
@EnableSwagger2
@EnableJpaRepositories
@EnableWebMvc
@EnableTransactionManagement
@EnableWebSecurity
@ComponentScan("com.fonada.masking")
public class FonadaDidMaskingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FonadaDidMaskingApplication.class, args);
	}

}
