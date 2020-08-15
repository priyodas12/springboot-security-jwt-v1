package io.springlab.springbootsecurityjwtv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("io.springlab.springbootsecurityjwtv1.*")
public class SpringbootSecurityJwtV1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSecurityJwtV1Application.class, args);
	}

}
