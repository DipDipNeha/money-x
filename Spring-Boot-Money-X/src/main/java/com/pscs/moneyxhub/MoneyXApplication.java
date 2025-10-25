package com.pscs.moneyxhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan 
public class MoneyXApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyXApplication.class, args);
	}

}
