package com.bridgelabz.fundoonotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.bridgelabz.fundoonotes.dao.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class MsUserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsUserserviceApplication.class, args);
	}

}

