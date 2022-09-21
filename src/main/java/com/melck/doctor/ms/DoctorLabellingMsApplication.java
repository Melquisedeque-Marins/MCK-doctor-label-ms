package com.melck.doctor.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DoctorLabellingMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoctorLabellingMsApplication.class, args);
	}

}
