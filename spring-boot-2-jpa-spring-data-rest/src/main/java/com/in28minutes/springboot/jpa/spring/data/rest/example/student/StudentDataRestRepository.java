package com.in28minutes.springboot.jpa.spring.data.rest.example.student;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "students", collectionResourceRel = "students")
public interface StudentDataRestRepository extends PagingAndSortingRepository<Student, Long>{

    Student findStudentById(@Param("id") Long id);

    List<Student> findStudentsByName(@Param("name") String name);

    Student findStudentByPassportNumber(@Param("pass") String pass);

}
