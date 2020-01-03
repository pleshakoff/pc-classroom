package com.parcom.classroom.services.add;

import com.parcom.classroom.SpringSecurityTestConfiguration;
import com.parcom.classroom.model.group.Group;
import com.parcom.classroom.model.group.GroupService;
import com.parcom.classroom.model.school.School;
import com.parcom.classroom.model.school.SchoolService;
import com.parcom.classroom.model.student.Student;
import com.parcom.classroom.model.student.StudentService;
import com.parcom.classroom.model.user.User;
import com.parcom.classroom.model.user.UserCreateDto;
import com.parcom.classroom.model.user.UserService;
import com.parcom.classroom.model.user.UserServiceImplTestConfiguration;
import com.parcom.exceptions.NotFoundParcomException;
import com.parcom.security_client.UserUtils;
import com.sun.xml.bind.v2.model.core.ID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.parcom.classroom.SpringSecurityTestConfiguration.ID_USER_ADMIN;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {AddServiceImplTestConfiguration.class})
class AddServiceImplTest {

    private static final String EMAIL = "email";
    private static final String PASS = "pass";
    private static final long ID_STUDENT = 1L;
    private static final long ID_GROUP = 1L;
    private static final long ID_SCHOOL = 1L;
    private static final String NAME_SCHOOL = "nameSchool";
    private static final String NAME_GROUP = "nameGroup";

    @Autowired
    AddService addService;

    @MockBean
    GroupService groupService;
    @MockBean StudentService studentService;
    @MockBean SchoolService schoolService;
    @MockBean UserService userService;


    @Test
    void registerByGroup() {
        AddMemberDto addMemberDto = new AddMemberDto(EMAIL, PASS,PASS, ID_STUDENT, ID_GROUP);
        Mockito.when(userService.create(EMAIL)).thenReturn(User.builder().id(ID_USER_ADMIN).email(EMAIL).build());
        User user = addService.registerByGroup(addMemberDto);

        assertAll(
                () -> Assertions.assertEquals(ID_USER_ADMIN,user.getId()),
                () -> Assertions.assertEquals(EMAIL,user.getEmail())
        );


    }

    @Test
    void registerByGroupWithoutGroup() {
        AddMemberDto addMemberDto = new AddMemberDto(EMAIL, PASS,PASS, ID_STUDENT, null);
        Assertions.assertThrows(NotFoundParcomException.class, () -> addService.registerByGroup(addMemberDto));
   }


    @Test
    void registerByStudent() {
        AddParentDto addParentDto = new AddParentDto(EMAIL, PASS,PASS, ID_STUDENT);
        Mockito.when(userService.create(EMAIL)).thenReturn(User.builder().id(ID_USER_ADMIN).email(EMAIL).build());
        Group group = Group.builder().id(ID_GROUP).build();
        Student student = Student.builder().id(ID_STUDENT).group(group).build();

        Mockito.when(studentService.getById(ID_STUDENT)).thenReturn(student);


        User user = addService.registerByStudent(addParentDto);
        assertAll(
                () -> Assertions.assertEquals(ID_USER_ADMIN,user.getId()),
                () -> Assertions.assertEquals(EMAIL,user.getEmail())
        );



    }


    @Test
    void registerByStudentEmptyStudent() {
        AddParentDto addParentDto = new AddParentDto(EMAIL, PASS,PASS, null);
        Assertions.assertThrows(NotFoundParcomException.class, () -> addService.registerByStudent(addParentDto));
    }


    @Test
    void registerNewGroup() {

        AddGroupDto addGroupDto = new AddGroupDto(EMAIL, PASS,PASS, ID_SCHOOL, NAME_SCHOOL, NAME_GROUP);
        Mockito.when(userService.create(EMAIL)).thenReturn(User.builder().id(ID_USER_ADMIN).email(EMAIL).build());

        School school = School.builder().name(NAME_SCHOOL).id(ID_SCHOOL).build();

        Mockito.when(schoolService.getOrCreate(ID_SCHOOL,NAME_SCHOOL)).thenReturn(school);

        Group group = Group.builder().id(ID_GROUP).name(NAME_GROUP).build();
        Mockito.when(groupService.create(NAME_GROUP,school)).thenReturn(group);


        User user = addService.registerNewGroup(addGroupDto);
        assertAll(
                () -> Assertions.assertEquals(ID_USER_ADMIN,user.getId()),
                () -> Assertions.assertEquals(EMAIL,user.getEmail())
        );





    }
}