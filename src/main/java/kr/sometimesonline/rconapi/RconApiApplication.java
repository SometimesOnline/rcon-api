package kr.sometimesonline.rconapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// 스프링 시큐리티 임시 비활성화.
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class RconApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RconApiApplication.class, args);
	}

}
