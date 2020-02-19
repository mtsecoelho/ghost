package br.com.splnet.ghost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GhostApplication {

	public static void main(String[] args) {
		SpringApplication.run(GhostApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
