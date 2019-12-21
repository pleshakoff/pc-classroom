package com.parcom.classroom.model.student;

import org.springframework.security.access.annotation.Secured;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface StudentService {

    Student getById(@NotNull Long idStudent);

    Student getCurrentStudent();

    Student getMyStudent(Long id);

    List<Student> getMyStudents(Long idGroup);

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    List<Student> getStudents();

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    Student create(StudentDto studentDTO);

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    Student update(Long id, StudentDto studentDTO);

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    void delete(Long id);
}
