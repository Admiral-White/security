package com.farmmart.security;

import com.farmmart.security.security.data.repository.UserRepository;
import com.farmmart.security.security.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}


	@Bean
	public UserService userService(){


		UserRepository userRepository;

		return new UserService();
	}

}
