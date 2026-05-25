package me.isoham.lmsbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LmsbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsbackendApplication.class, args);
	}

}
