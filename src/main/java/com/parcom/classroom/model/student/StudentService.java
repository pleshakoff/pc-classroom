package com.parcom.classroom.model.student;

import com.parcom.classroom.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final  StudentToUserRepository studentToUserRepository;


    public Student getById(@NotNull Long idStudent) {
        return studentRepository.findById(idStudent).orElseThrow(() -> new EntityNotFoundException("Ученик не найден"));
    }

    public Student getByOrNull(@NotNull Long idStudent) {
        return studentRepository.findById(idStudent).orElse(null);
    }

    public StudentToUser linkStudentToUser(Student student, User user) {
        return studentToUserRepository.save(StudentToUser.builder().student(student).user(user).build());
    }



}
