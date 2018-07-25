package org.quidcain.jad.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JadBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(JadBackendApplication.class, args);
	}
}
