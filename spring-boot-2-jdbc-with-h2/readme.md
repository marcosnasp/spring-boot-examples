<!---
Current Directory : /in28Minutes/git/spring-boot-examples/spring-boot-2-jdbc-with-h2
-->

## Complete Code Example


### Gradle

```gradle
apply plugin: 'java'
apply plugin: 'maven'

group = 'com.in28minutes.springboot.rest.example'
version = '0.0.1-SNAPSHOT'

description = """spring-boot-2-jdbc-with-h2"""

sourceCompatibility = 1.8
targetCompatibility = 1.8

tasks.withType(JavaCompile) {
	options.encoding = 'UTF-8'
}

repositories {
        
     maven { url "https://repo.spring.io/snapshot" }
     maven { url "https://repo.spring.io/milestone" }
     maven { url "http://repo.maven.apache.org/maven2" }
}
dependencies {
    compile group: 'org.springframework.boot', name: 'spring-boot-starter-jdbc', version:'2.0.0.RELEASE'
    compile group: 'org.springframework.boot', name: 'spring-boot-starter-web', version:'2.0.0.RELEASE'
    compile group: 'org.springframework', name: 'spring-test', version:'5.0.4.RELEASE'
    runtime group: 'org.springframework.boot', name: 'spring-boot-devtools', version:'2.0.0.RELEASE'
    runtime group: 'com.h2database', name: 'h2', version:'1.4.196'
    testCompile group: 'org.springframework.boot', name: 'spring-boot-starter-test', version:'2.0.0.RELEASE'
    testCompile group: 'org.assertj', name: 'assertj-core', version:'3.9.1'
}
```
---

### /src/main/java/com/in28minutes/springboot/jdbc/h2/example/SpringBoot2JdbcWithH2Application.java

```java
package com.in28minutes.springboot.jdbc.h2.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.in28minutes.springboot.jdbc.h2.example.student.Student;
import com.in28minutes.springboot.jdbc.h2.example.student.StudentJdbcRepository;

@SpringBootApplication
public class SpringBoot2JdbcWithH2Application implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	StudentJdbcRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot2JdbcWithH2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		logger.info("Student id 10001 -> {}", repository.findById(10001L));

		logger.info("Inserting -> {}", repository.insert(new Student(10010L, "John", "A1234657")));

		logger.info("Update 10003 -> {}", repository.update(new Student(10001L, "Name-Updated", "New-Passport")));

		repository.deleteById(10002L);

		logger.info("All users -> {}", repository.findAll());
	}
}
```
---

### /src/main/java/com/in28minutes/springboot/jdbc/h2/example/student/Student.java

```java
package com.in28minutes.springboot.jdbc.h2.example.student;

public class Student {
	private Long id;
	private String name;
	private String passportNumber;

	public Student() {
		super();
	}

	public Student(Long id, String name, String passportNumber) {
		super();
		this.id = id;
		this.name = name;
		this.passportNumber = passportNumber;
	}

	public Student(String name, String passportNumber) {
		super();
		this.name = name;
		this.passportNumber = passportNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	@Override
	public String toString() {
		return String.format("Student [id=%s, name=%s, passportNumber=%s]", id, name, passportNumber);
	}

}
```
---

### /src/main/java/com/in28minutes/springboot/jdbc/h2/example/student/StudentJdbcRepository.java

```java
package com.in28minutes.springboot.jdbc.h2.example.student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class StudentJdbcRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;

	class StudentRowMapper implements RowMapper<Student> {
		@Override
		public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
			Student student = new Student();
			student.setId(rs.getLong("id"));
			student.setName(rs.getString("name"));
			student.setPassportNumber(rs.getString("passport_number"));
			return student;
		}

	}

	public List<Student> findAll() {
		return jdbcTemplate.query("select * from student", new StudentRowMapper());
	}

	public Student findById(long id) {
		return jdbcTemplate.queryForObject("select * from student where id=?", new Object[] { id },
				new BeanPropertyRowMapper<Student>(Student.class));
	}

	public int deleteById(long id) {
		return jdbcTemplate.update("delete from student where id=?", new Object[] { id });
	}

	public int insert(Student student) {
		return jdbcTemplate.update("insert into student (id, name, passport_number) " + "values(?,  ?, ?)",
				new Object[] { student.getId(), student.getName(), student.getPassportNumber() });
	}

	public int update(Student student) {
		return jdbcTemplate.update("update student " + " set name = ?, passport_number = ? " + " where id = ?",
				new Object[] { student.getName(), student.getPassportNumber(), student.getId() });
	}

}
```
---

### /src/main/resources/application.properties

```properties
# Enabling H2 Console
spring.h2.console.enabled=true
#Turn Statistics on
spring.jpa.properties.hibernate.generate_statistics=true
logging.level.org.hibernate.stat=debug
# Show all queries
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.type=trace
```
---

### /src/main/resources/data.sql

```
insert into student
values(10001,'Ranga', 'E1234567');

insert into student
values(10002,'Ravi', 'A1234568');
```
---

### /src/main/resources/schema.sql

```
create table student
(
   id integer not null,
   name varchar(255) not null,
   passport_number varchar(255) not null,
   primary key(id)
);
```
---

### com.in28minutes.springboot.jdbc.h2.example.TestRepositoryConfiguration
### Bean Configuration to be used on Test Cases

```java
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
```

### Test against H2 Database
```java
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
```

### Tests with Mockito
```java
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
```
---
