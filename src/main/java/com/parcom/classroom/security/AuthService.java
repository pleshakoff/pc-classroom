package com.parcom.classroom.security;

import com.parcom.classroom.model.group.Group;
import com.parcom.classroom.model.group.GroupRepository;
import com.parcom.classroom.model.student.Student;
import com.parcom.classroom.model.student.StudentRepository;
import com.parcom.classroom.model.user.*;
import com.parcom.classroom.security.dto.UserAuthDTO;
import com.parcom.classroom.security.dto.UserRegisterByGroupDTO;
import com.parcom.classroom.security.dto.UserRegisterByStudentDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public
class AuthService {

    private  final  GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;
    private  final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final StudentRepository studentRepository;

    public AuthService(GroupRepository groupRepository, PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager, StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.studentRepository = studentRepository;
    }

   public User registerByGroup(UserRegisterByGroupDTO userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getPasswordConfirm())) {
            throw new RuntimeException("Пароль и его подтверждение должны совпадать");
        }
       if (userDTO.getIdGroup() == null) {
           throw new RuntimeException("Необходимо выбрать класс");
       }

       Group group = groupRepository.findById(userDTO.getIdGroup()).orElseThrow(() -> new EntityNotFoundException("Группа не найдена"));

       Student student = null;
       if (userDTO.getIdStudent() != null) {
           student = studentRepository.findById(userDTO.getIdStudent()).orElse(null);
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
        Student student = studentRepository.findById(userDTO.getIdStudent()).orElseThrow(() -> new EntityNotFoundException("Ученик не найден"));

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


    TokenResource authenticate(UserAuthDTO userAuthDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(userAuthDTO.getUsername(), userAuthDTO.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsPC userDetail = (UserDetailsPC) authentication.getPrincipal();
        return new TokenResource(TokenUtils.createToken(userDetail),userDetail.getId());
    }
}
