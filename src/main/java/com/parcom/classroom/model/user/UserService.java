package com.parcom.classroom.model.user;

import com.parcom.classroom.model.group.Group;
import com.parcom.classroom.model.group.GroupRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private  final  GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;
    private  final  UserRepository userRepository;

    public UserService(GroupRepository groupRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

   public User registerByGroup(UserRegisterByGroupDTO userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getPasswordConfirm())) {
            throw new RuntimeException("Пароль и его подтверждение должны совпадать");
        }
        Group group = groupRepository.findById(userDTO.getGroupId()).orElseThrow(() -> new RuntimeException("Группа не найдена"));


        User user = User.builder().email(userDTO.getEmail()).
                                   enabled(true).
                                   firstName(userDTO.getFirstName()).
                                   middleName(userDTO.getMiddleName()).
                                   familyName(userDTO.getFamilyName()).
                                   username(userDTO.getUsername()).
                                   phone(userDTO.getPhone()).
                                   password(passwordEncoder.encode(userDTO.getPassword())).
                                   role(Role.MEMBER).
                                   group(group).build();

        return userRepository.save(user);

    }


}
