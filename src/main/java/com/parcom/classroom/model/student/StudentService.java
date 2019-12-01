package com.parcom.classroom.model.student;

import com.parcom.classroom.model.user.User;
import com.parcom.classroom.security.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    public static final String STUDENT_NOT_FOUND = "Student not found";
    private final StudentRepository studentRepository;
    private final StudentToUserRepository studentToUserRepository;


    public Student getById(@NotNull Long idStudent) {
        return studentRepository.findById(idStudent).orElseThrow(EntityNotFoundException::new);
    }

    public Student getByOrNull(@NotNull Long idStudent) {
        return studentRepository.findById(idStudent).orElse(null);
    }

    public StudentToUser linkStudentToUser(Student student, User user) {
        return studentToUserRepository.save(StudentToUser.builder().student(student).user(user).build());
    }

    List<Student> getCurrentStudents() {
        return studentToUserRepository.getCurrentStudents(UserUtils.getIdUser(), UserUtils.getIdGroup());
    }

    Student getCurrentStudent(Long id) {
        return studentToUserRepository.getCurrentStudents(UserUtils.getIdUser(), UserUtils.getIdGroup()).
                stream().
                filter(student -> student.getId().equals(id)).
                findFirst().orElseThrow(EntityNotFoundException::new);
    }

}
