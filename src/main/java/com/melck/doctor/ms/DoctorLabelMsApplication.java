package com.melck.doctor.ms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DoctorLabelMsApplication {

	private static Logger logger = LoggerFactory.getLogger(DoctorLabelMsApplication.class);
	public static void main(String[] args) {
		logger.info("Starting Doctor Label API ");
		SpringApplication.run(DoctorLabelMsApplication.class, args);
		logger.info("Doctor Label API is ready to receive requests");
	}

}
