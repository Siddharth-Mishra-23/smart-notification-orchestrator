package com.notification.orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAsync
public class SmartNotificationOrchestratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartNotificationOrchestratorApplication.class, args);
	}

}
