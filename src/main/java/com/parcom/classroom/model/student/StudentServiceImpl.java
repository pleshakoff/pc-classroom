package com.parcom.classroom.model.student;

import com.parcom.classroom.model.group.GroupService;
import com.parcom.classroom.services.sync.SyncStudentEvent;
import com.parcom.exceptions.NotFoundParcomException;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private static final String STUDENT_NOT_FOUND = "student.not_found";
    private final StudentRepository studentRepository;
    private final StudentToUserRepository studentToUserRepository;
    private final GroupService groupService;
    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    public Student getById(@NotNull Long idStudent) {
        return studentRepository.findById(idStudent).orElseThrow(() -> new NotFoundParcomException(STUDENT_NOT_FOUND));
    }


    @Override
    public Student getCurrentStudent() {
        if (UserUtils.getIdStudent() == null)
            return null;
        return getById(UserUtils.getIdStudent());
    }


    @Override
    public List<Student> getMyStudents(Long idGroup) {
        if (idGroup == null) {
            return studentToUserRepository.getMyStudents(UserUtils.getIdUser());
        } else
            return studentToUserRepository.getMyStudentsInGroup(UserUtils.getIdUser(), idGroup);
    }

    @Override
    public Student getMyStudent(Long id) {
        if (UserUtils.getRole().equals(UserUtils.ROLE_PARENT)) {
            return getMyStudents(null).
                    stream().
                    filter(student -> student.getId().equals(id)).
                    findFirst().orElseThrow(() -> new NotFoundParcomException(STUDENT_NOT_FOUND));
        } else
            return getById(id);
    }


    @Override
    public List<Student> getStudents() {
        return studentRepository.getStudentsByGroup(UserUtils.getIdGroup());
    }

    @Override
    @Transactional
    public Student create(StudentDto studentDTO) {
        Student student = studentRepository.save(
                Student.builder().firstName(studentDTO.getFirstName()).
                        middleName(studentDTO.getMiddleName()).
                        familyName(studentDTO.getFamilyName()).
                        birthDay(studentDTO.getBirthDay()).
                        group(groupService.getCurrentGroup()).
                        build()
        );
        applicationEventPublisher.publishEvent(new SyncStudentEvent(this, student.getId()));
        return student;
    }

    @Override
    @Transactional
    public Student update(Long id, StudentDto studentDTO) {
        Student student = getById(id);
        student.setBirthDay(studentDTO.getBirthDay());
        student.setFirstName(studentDTO.getFirstName());
        student.setFamilyName(studentDTO.getFamilyName());
        student.setMiddleName(studentDTO.getMiddleName());
        Student updated = studentRepository.save(
                student
        );
        applicationEventPublisher.publishEvent(new SyncStudentEvent(this, student.getId()));
        return updated;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        studentToUserRepository.deleteAllByStudent(getById(id));
        studentRepository.deleteById(id);
        applicationEventPublisher.publishEvent(new SyncStudentEvent(this, id));
    }


}
