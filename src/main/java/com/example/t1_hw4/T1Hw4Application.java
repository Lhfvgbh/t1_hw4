package com.example.t1_hw4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class T1Hw4Application {

	public static void main(String[] args) {
		SpringApplication.run(T1Hw4Application.class, args);
	}

}
