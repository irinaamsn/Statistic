package com.spring.statistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StatisticApplication {
	public static void main(String[] args) {
		SpringApplication.run(StatisticApplication.class, args);
	}
}
