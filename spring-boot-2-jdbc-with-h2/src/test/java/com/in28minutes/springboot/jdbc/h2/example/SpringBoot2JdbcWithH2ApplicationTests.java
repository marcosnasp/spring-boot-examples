package com.in28minutes.springboot.jdbc.h2.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.in28minutes.springboot.jdbc.h2.example.student.Student;
import com.in28minutes.springboot.jdbc.h2.example.student.StudentJdbcRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@JdbcTest
@SpringBootTest
@ContextConfiguration(classes = TestRepositoryConfiguration.class, 
		loader = AnnotationConfigContextLoader.class)
public class SpringBoot2JdbcWithH2ApplicationTests {
	
	@Autowired
	StudentJdbcRepository studentRepo;
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void selectOneSpecificStudentTest() {
		final Student studentLocal = new Student(10001L, "Ranga", "E1234567");

		final long id = 10001;
		final Student student = studentRepo.findById(id);

		assertThat(student).isNotNull();
		assertThat(studentLocal).isEqualToComparingFieldByField(student);
	}

	@Test
	public void selectAllStudentsTest() {
		final List<Student> allStudents = studentRepo.findAll();
		
		assertThat(allStudents).isNotEmpty();
		assertThat(allStudents).hasSize(2);
	}
	
	@Test
	public void deleteOneStudentById() {
		final int rowsAffected = 1;
		final long id = 10001;
		
		assertThat(studentRepo.deleteById(id)).isEqualTo(rowsAffected);
		assertThat(studentRepo.findAll()).hasSize(1);
	}
	
	@Test
	public void insertOneStudentTest() {
		final Student studentLocal = new Student(10003L, "Michael", "E12345679");

		int rowsAffected = studentRepo.insert(studentLocal);
		assertThat(rowsAffected).isEqualTo(1);
	}
	
}
