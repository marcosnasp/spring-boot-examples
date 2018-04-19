package com.in28minutes.springboot.jdbc.h2.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.in28minutes.springboot.jdbc.h2.example.student.StudentJdbcRepository;

@Configuration
public class TestRepositoryConfiguration {

	@Bean
	public StudentJdbcRepository getStudentJdbcRepository() {
		return new StudentJdbcRepository();
	}
	
}