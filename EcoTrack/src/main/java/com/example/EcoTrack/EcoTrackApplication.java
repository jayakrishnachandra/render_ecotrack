package com.example.EcoTrack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class EcoTrackApplication {

	// @Autowired
	// private EmailSenderService emailSenderService;
	public static void main(String[] args) {
		SpringApplication.run(EcoTrackApplication.class, args);
	}
	// @EventListener(ApplicationReadyEvent.class)
	// public void triggerMail() {
	// 	emailSenderService.sendEmail("vemanajayakrishnachandra@gmail.com", "Im ecotrack", "Test mail");
	// }

}
