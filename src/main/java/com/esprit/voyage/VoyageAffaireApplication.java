package com.esprit.voyage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableAspectJAutoProxy
@ComponentScan("com.esprit.voyage")
@EnableJpaRepositories("com.esprit.voyage.repository")
@EntityScan(basePackages = {"com.esprit.voyage.entity"})
public class VoyageAffaireApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(VoyageAffaireApplication.class, args);
	}	
}
