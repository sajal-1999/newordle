package com.newordle.newordle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewordleApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewordleApplication.class, args);
		System.out.println("Let the game begin!");
	}

}
