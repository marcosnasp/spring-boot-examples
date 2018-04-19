package com.in28minutes.springboot.jdbc.h2.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.in28minutes.springboot.jdbc.h2.example.student.Student;
import com.in28minutes.springboot.jdbc.h2.example.student.StudentJdbcRepository;

@RunWith(MockitoJUnitRunner.class)
public class SpringBoot2JdbcWithH2ApplicationWithMockTests {

	@Mock
	StudentJdbcRepository studentRepo;

	@Test
	public void contextLoads() {
	}

	@Test
	public void selectOneSpecificStudentTest() {
		final Student studentLocal = new Student(10001L, "Ranga", "E1234567");
		final long id = 10001;

		when(studentRepo.findById(id)).thenReturn(studentLocal);

		final Student student = studentRepo.findById(id);

		assertThat(student).isNotNull();
		assertThat(studentLocal).isEqualToComparingFieldByField(student);
	}

	@Test
	public void selectAllStudentsTest() {
		when(studentRepo.findAll()).thenReturn(new ArrayList<Student>(
				Arrays.asList(new Student(10001L, "Ranga", "E1234567"), new Student(10002L, "Ravi", "A1234568"))));

		final List<Student> allStudents = studentRepo.findAll();

		assertThat(allStudents).isNotEmpty();
		assertThat(allStudents).hasSize(2);
	}

	@Test
	public void deleteOneStudentTest() {
		final long id = 10001;
		final int rowsAffected = 1;

		when(studentRepo.deleteById(id)).thenReturn(rowsAffected);

		assertThat(studentRepo.deleteById(id)).isEqualTo(rowsAffected);
	}

	@Test
	public void insertOneStudentTest() {
		final int rowsAffected = 1;
		final Student student = new Student(10003L, "Michael", "A1234569");
		
		when(studentRepo.insert(student)).thenReturn(rowsAffected);
		
		assertThat(studentRepo.insert(student)).isEqualTo(rowsAffected);
	}

	@Test
	public void updateOneStudentTest() {
		final int rowsAffected = 1;
		final Student student = new Student(10001L, "John", "E1234567");
		
		when(studentRepo.update(student)).thenReturn(1);
		
		assertThat(studentRepo.update(student)).isEqualTo(rowsAffected);
	}

}
