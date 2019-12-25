package com.parcom.classroom.model.student;

import com.parcom.classroom.model.group.GroupService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class StudentServiceImplTestConfiguration {

    @Bean
    StudentService studentService(StudentRepository studentRepository, StudentToUserRepository studentToUserRepository, GroupService groupService) {
        return  new StudentServiceImpl(studentRepository,studentToUserRepository,groupService);
    }
}
