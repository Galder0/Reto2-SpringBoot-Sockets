	package com.reto.elorchatS;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ElorchatSApplication {

	public static void main(String[] args) {
		//SpringApplication.run(ElorchatSApplication.class, args);
		
		SpringApplication application = new SpringApplication(ElorchatSApplication.class);
		application.setAdditionalProfiles("ssl");
        application.run(args);
	}
	
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
