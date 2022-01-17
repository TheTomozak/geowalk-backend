package com.example.geowalk;

import com.example.geowalk.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class GeoWalkApplication implements CommandLineRunner {

	@Resource
	ImageService imageService;

	public static void main(String[] args) {
		SpringApplication.run(GeoWalkApplication.class, args);
	}

	@Override
	public void run(String... arg) {
		imageService.init();
	}
}
