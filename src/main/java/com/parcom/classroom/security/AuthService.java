package com.parcom.classroom.security;

import com.parcom.classroom.model.group.Group;
import com.parcom.classroom.model.group.GroupService;
import com.parcom.classroom.model.school.School;
import com.parcom.classroom.model.school.SchoolService;
import com.parcom.classroom.model.student.Student;
import com.parcom.classroom.model.student.StudentService;
import com.parcom.classroom.model.user.Role;
import com.parcom.classroom.model.user.TokenResource;
import com.parcom.classroom.model.user.User;
import com.parcom.classroom.model.user.UserRepository;
import com.parcom.classroom.security.dto.UserAuthDTO;
import com.parcom.classroom.security.dto.UserRegisterByGroupDTO;
import com.parcom.classroom.security.dto.UserRegisterByStudentDTO;
import com.parcom.classroom.security.dto.UserRegisterNewGroupDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GroupService groupService;
    private final PasswordEncoder passwordEncoder;
    private  final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final StudentService studentService;
    private final SchoolService schoolService;


   public User registerByGroup(UserRegisterByGroupDTO userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getPasswordConfirm())) {
            throw new RuntimeException("Пароль и его подтверждение должны совпадать");
        }
       if (userDTO.getIdGroup() == null) {
           throw new RuntimeException("Необходимо выбрать класс");
       }

       Group group = groupService.getById(userDTO.getIdGroup());

       Student student = null;
       if (userDTO.getIdStudent() != null) {
           student = studentService.getByOrNull(userDTO.getIdStudent());
       }

           User user = User.builder().email(userDTO.getEmail()).
                   enabled(true).
                   firstName(userDTO.getFirstName()).
                   middleName(userDTO.getMiddleName()).
                   familyName(userDTO.getFamilyName()).
                   username(userDTO.getUsername()).
                   phone(userDTO.getPhone()).
                   password(passwordEncoder.encode(userDTO.getPassword())).
                   role(Role.MEMBER).
                   student(student).
                   group(group).build();
        return userRepository.save(user);
    }


    public User registerByStudent(UserRegisterByStudentDTO userDTO) {
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
                password(passwordEncoder.encode(userDTO.getPassword())).
                role(Role.USER).
                student(student).
                group(student.getGroup()).build();
        return userRepository.save(user);
    }

    public User registerNewGroup(UserRegisterNewGroupDTO userDTO) {
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
                password(passwordEncoder.encode(userDTO.getPassword())).
                role(Role.ADMIN).
                group(group).build();
        return userRepository.save(user);
    }



    TokenResource authenticate(UserAuthDTO userAuthDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(userAuthDTO.getUsername(), userAuthDTO.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsPC userDetail = (UserDetailsPC) authentication.getPrincipal();
        return new TokenResource(TokenUtils.createToken(userDetail),userDetail.getId());
    }
}
