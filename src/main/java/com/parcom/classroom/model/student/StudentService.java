package com.parcom.classroom.model.student;

import com.parcom.classroom.model.group.GroupService;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentToUserRepository studentToUserRepository;
    private final GroupService groupService;


    public Student getById(@NotNull Long idStudent) {
        return studentRepository.findById(idStudent).orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    public Student getByOrNull(@NotNull Long idStudent) {
        return studentRepository.findById(idStudent).orElse(null);
    }

    public StudentToUser linkStudentToUser(Student student, Long idUser) {
        return studentToUserRepository.save(StudentToUser.builder().student(student).idUser(idUser).build());
    }

    List<Student> getMyStudents() {
        return studentToUserRepository.getCurrentStudents(UserUtils.getIdUser(), UserUtils.getIdGroup());
    }

    Student getMyStudent(Long id) {
        if (UserUtils.getRole().equals(UserUtils.ROLE_PARENT)) {
            return studentToUserRepository.getCurrentStudents(UserUtils.getIdUser(), UserUtils.getIdGroup()).
                    stream().
                    filter(student -> student.getId().equals(id)).
                    findFirst().orElseThrow(EntityNotFoundException::new);
        }
        else
           return  getById(id);
    }


    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    List<Student> getStudents() {
        return studentRepository.getStudents(UserUtils.getIdGroup());
    }

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    Student create(StudentDto studentDTO){
        return studentRepository.save(
                Student.builder().firstName(studentDTO.getFirstName()).
                                  middleName(studentDTO.getMiddleName()).
                                  familyName(studentDTO.getFamilyName()).
                                  birthDay(studentDTO.getBirthDay()).
                                  group(groupService.getCurrentGroup()).
                                  build()
        );
    }

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    Student update(Long id, StudentDto studentDTO)
    {
        Student student = getById(id);
        student.setBirthDay(studentDTO.getBirthDay());
        student.setFirstName(studentDTO.getFirstName());
        student.setFamilyName(studentDTO.getFamilyName());
        student.setMiddleName(studentDTO.getMiddleName());
        return studentRepository.save(
                student
        );
    }

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    void delete(Long id)
    {
        studentRepository.deleteById(id); ;
    }


}
