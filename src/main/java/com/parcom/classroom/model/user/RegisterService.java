package com.parcom.classroom.model.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

//    private final GroupService groupService;
//    private final PasswordEncoder passwordEncoder;
//    private  final UserRepository userRepository;
//    private final AuthenticationManager authenticationManager;
//    private final StudentService studentService;
//    private final SchoolService schoolService;
//
//
//   public User registerByGroup(UserRegisterByGroupDto userDTO) {
//        if (!userDTO.getPassword().equals(userDTO.getPasswordConfirm())) {
////          return user;
//
//   }
//
//
//    public User registerByStudent(UserRegisterByStudentDto userDTO) {
//        if (!userDTO.getPassword().equals(userDTO.getPasswordConfirm())) {
//            throw new RuntimeException("Пароль и его подтверждение должны совпадать");
//        }
//        if (userDTO.getIdStudent() == null) {
//            throw new RuntimeException("Необходимо выбрать ученика");
//        }
//
//        Student student = studentService.getById(userDTO.getIdStudent());
//
//        User user = User.builder().email(userDTO.getEmail()).
//                enabled(true).
//                firstName(userDTO.getFirstName()).
//                middleName(userDTO.getMiddleName()).
//                familyName(userDTO.getFamilyName()).
//                username(userDTO.getUsername()).
//                phone(userDTO.getPhone()).
//                password(passwordEncoder.encode(userDTO.getPassword())).
//                role("ROLE_USER").
//                group(student.getGroup()).build();
//
//
//        userRepository.save(user);
//        studentService.linkStudentToUser(student,user);
//        return user;
//    }
//
//    User registerNewGroup(UserRegisterNewGroupDto userDTO) {
//        if (!userDTO.getPassword().equals(userDTO.getPasswordConfirm())) {
//            throw new RuntimeException("Пароль и его подтверждение должны совпадать");
//        }
//
//        if (userDTO.getIdSchool() == null && userDTO.getNameSchool() == null)
//            throw new  RuntimeException("Необходимо выбрать школу или ввести наименование");
//
//        School school = schoolService.getOrCreate(userDTO.getIdSchool(),userDTO.getNameSchool());
//        Group group = groupService.create(userDTO.getNameGroup(),school);
//
//        User user = User.builder().email(userDTO.getEmail()).
//                enabled(true).
//                firstName(userDTO.getFirstName()).
//                middleName(userDTO.getMiddleName()).
//                familyName(userDTO.getFamilyName()).
//                username(userDTO.getUsername()).
//                phone(userDTO.getPhone()).
//                password(passwordEncoder.encode(userDTO.getPassword())).
//                role("ROLE_ADMIN").
//                group(group).build();
//        return userRepository.save(user);
//    }
//
//

}
