package com.areo.design.holidays;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyBargainHolidaysApplication {

	@Autowired
	private DataPreparator dataPreparator;

	public static void main(String[] args) {
		SpringApplication.run(MyBargainHolidaysApplication.class, args);
	}

	@Bean
	InitializingBean prepareData() {
		return () -> dataPreparator.prepare();
	}

}
