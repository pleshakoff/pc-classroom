package com.parcom.classroom.model.student;

import com.parcom.classroom.SpringSecurityTestConfiguration;
import com.parcom.classroom.model.group.GroupServiceImpl;
import com.parcom.exceptions.AccessDeniedParcomException;
import com.parcom.exceptions.NotFoundParcomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.Arrays;

import static java.util.Optional.*;


@SpringBootTest(classes = {StudentServiceImplTestConfiguration.class,
SpringSecurityTestConfiguration.class})
public class StudentServiceImplTest {


    public static final long ID_STUDENT_ONE = 1L;
    public static final long ID_STUDENT_TWO = 2L;
    public static final long ID_USER_ADMIN = 1L;
    public static final long ID_GROUP_ONE = 1L;
    @Autowired
    StudentService studentService;

    @MockBean StudentRepository studentRepository;
    @MockBean StudentToUserRepository studentToUserRepository;
    @MockBean GroupServiceImpl groupServiceImpl;

    @BeforeEach
    void  initMocks(){

    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void getById() {
        Mockito.when(studentRepository.findById(ID_STUDENT_ONE)).thenReturn(of(Student.builder().id(ID_STUDENT_ONE).build()));
        Student student = studentService.getById(ID_STUDENT_ONE);
        Assertions.assertEquals(ID_STUDENT_ONE,student.getId());
    }

    @Test
    @WithUserDetails("childFreeMember@parcom.com")
    public void getCurrentStudentForUserWithoutStudent() {

        Assertions.assertNull(studentService.getCurrentStudent());
    }

    @Test
    @WithUserDetails("admin@parcom.com")
    public void getCurrentStudentNotFound() {
        Assertions.assertThrows(NotFoundParcomException.class, () -> {
            studentService.getCurrentStudent();
        });

    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void getMyStudents() {

        Mockito.when(studentToUserRepository.getMyStudents(ID_USER_ADMIN)).
                thenReturn(Arrays.asList(Student.builder().id(ID_STUDENT_ONE).build(),Student.builder().id(ID_STUDENT_TWO).build())
                );

        Assertions.assertEquals(2,studentService.getMyStudents(null).size());
    }

    @Test
    @WithUserDetails("admin@parcom.com")
    public void getMyStudentsByGroup() {

        Mockito.when(studentToUserRepository.getMyStudentsInGroup(ID_USER_ADMIN,ID_GROUP_ONE)).
                thenReturn(Arrays.asList(Student.builder().id(ID_STUDENT_ONE).build(),Student.builder().id(ID_STUDENT_TWO).build())
                );

        Assertions.assertEquals(2,studentService.getMyStudents(ID_GROUP_ONE).size());


    }

    @Test
    public void getMyStudentsByGroupUnauthorised() {
        Mockito.when(studentToUserRepository.getMyStudents(ID_USER_ADMIN)).
                thenReturn(Arrays.asList(Student.builder().id(ID_STUDENT_ONE).build(),Student.builder().id(ID_STUDENT_TWO).build())
                );
       Assertions.assertEquals(0,studentService.getMyStudents(1L).size());
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void getMyStudent() {
        Mockito.when(studentRepository.findById(ID_STUDENT_ONE)).thenReturn(of(Student.builder().id(ID_STUDENT_ONE).build()));
        Student student = studentService.getMyStudent(ID_STUDENT_ONE);
        Assertions.assertEquals(ID_STUDENT_ONE,student.getId());
    }

    @Test
    @WithUserDetails("member@parcom.com")
    public void getMyStudentNotFound() {
        Mockito.when(studentRepository.findById(ID_STUDENT_ONE)).thenReturn(of(Student.builder().id(ID_STUDENT_ONE).build()));
        Assertions.assertThrows(NotFoundParcomException.class, () -> {
            studentService.getMyStudent(ID_STUDENT_TWO);
        });
    }


    @Test
    @WithUserDetails("parent@parcom.com")
    public void getMyStudentWrongParent() {
        Mockito.when(studentRepository.findById(ID_STUDENT_ONE)).thenReturn(of(Student.builder().id(ID_STUDENT_ONE).build()));
        Assertions.assertThrows(NotFoundParcomException.class, () -> {
            studentService.getMyStudent(ID_STUDENT_ONE);
        });
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void getStudents() {
        Mockito.when(studentRepository.getStudentsByGroup(ID_GROUP_ONE)).
                thenReturn(Arrays.asList(Student.builder().id(ID_STUDENT_ONE).build(),Student.builder().id(ID_STUDENT_TWO).build())
                );

        Assertions.assertEquals(2,studentService.getStudents().size());
    }

    @Test
    @WithUserDetails("parent@parcom.com")
    public void getStudentsAccessDenied() {
        Assertions.assertThrows(AccessDeniedException.class, () -> {
            studentService.getStudents();
        });
    }

    @Test
    @WithUserDetails("fromAnotherGroup@parcom.com")
    public void getStudentsNotMyGroup() {
        Mockito.when(studentRepository.getStudentsByGroup(ID_GROUP_ONE)).
                thenReturn(Arrays.asList(Student.builder().id(ID_STUDENT_ONE).build(),Student.builder().id(ID_STUDENT_TWO).build())
                );
        Assertions.assertEquals(0,studentService.getStudents().size());
    }




    @Test
    public void create() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}