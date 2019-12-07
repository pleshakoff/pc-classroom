package com.parcom.classroom.model.user;

import com.parcom.classroom.model.group.Group;
import com.parcom.classroom.model.group.GroupService;
import com.parcom.classroom.model.school.School;
import com.parcom.classroom.model.school.SchoolService;
import com.parcom.classroom.model.student.Student;
import com.parcom.classroom.model.student.StudentService;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final GroupService groupService;
    private final StudentService studentService;
    private final SchoolService schoolService;
    private final UserRemote userRemote;


    @Transactional
    public User registerByGroup(UserRegisterByGroupDto userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getPasswordConfirm())) {
            throw new RuntimeException("Пароль и его подтверждение должны совпадать");
        }

        if (userDTO.getIdGroup() == null) {
            throw new RuntimeException("Необходимо выбрать класс");
        }

        groupService.getById(userDTO.getIdGroup());

        User user = User.builder().email(userDTO.getEmail()).
                enabled(true).
                firstName(userDTO.getFirstName()).
                middleName(userDTO.getMiddleName()).
                familyName(userDTO.getFamilyName()).
                username(userDTO.getUsername()).
                phone(userDTO.getPhone()).
                password(userDTO.getPassword()).
                role(UserUtils.ROLE_MEMBER).
                idGroup(userDTO.getIdGroup()).build();

        user = userRemote.register(user);

        try {

            if (userDTO.getIdStudent() != null) {
                Student student = studentService.getByOrNull(userDTO.getIdStudent());
                studentService.linkStudentToUser(student,user.getId());
            }
        }
        catch (Exception e)
        {
            userRemote.delete(user.getId());
            throw e;
        }
        return user;

    }


    @Transactional
    User registerByStudent(UserRegisterByStudentDto userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getPasswordConfirm())) {
            throw new RuntimeException("Пароль и его подтверждение должны совпадать");
        }


        if (userDTO.getIdStudent() == null) {
            throw new RuntimeException("Необходимо выбрать ученика");
        }
        Student student = studentService.getById(userDTO.getIdStudent());


        User user = User.builder().email(userDTO.getEmail()).
                enabled(true).
                firstName(userDTO.getFirstName()).
                middleName(userDTO.getMiddleName()).
                familyName(userDTO.getFamilyName()).
                username(userDTO.getUsername()).
                phone(userDTO.getPhone()).
                password(userDTO.getPassword()).
                role(UserUtils.ROLE_PARENT).
                idGroup(student.getGroup().getId()).build();

        user = userRemote.register(user);

        try {
           studentService.linkStudentToUser(student,user.getId());
        }
        catch (Exception e)
        {
            userRemote.delete(user.getId());
            throw e;
        }
        return user;
    }

    @Transactional
    User registerNewGroup(UserRegisterNewGroupDto userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getPasswordConfirm())) {
            throw new RuntimeException("Пароль и его подтверждение должны совпадать");
        }


          if (userDTO.getIdSchool() == null && userDTO.getNameSchool() == null)
            throw new  RuntimeException("Необходимо выбрать школу или ввести наименование");

        School school = schoolService.getOrCreate(userDTO.getIdSchool(),userDTO.getNameSchool());
        Group group = groupService.create(userDTO.getNameGroup(),school);

        User user = User.builder().email(userDTO.getEmail()).
                enabled(true).
                firstName(userDTO.getFirstName()).
                middleName(userDTO.getMiddleName()).
                familyName(userDTO.getFamilyName()).
                username(userDTO.getUsername()).
                phone(userDTO.getPhone()).
                password(userDTO.getPassword()).
                role(UserUtils.ROLE_ADMIN).
                idGroup(group.getId()).build();

        return userRemote.register(user);
    }



}
