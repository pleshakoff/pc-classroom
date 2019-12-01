package com.parcom.classroom.model.user;

import com.parcom.classroom.model.group.Group;
import com.parcom.classroom.model.group.GroupRepository;
import com.parcom.classroom.security.TokenUtils;
import com.parcom.classroom.security.UserDetailsPC;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private  final  GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;
    private  final  UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public UserService(GroupRepository groupRepository, PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.groupRepository = groupRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
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


    public TokenResource authenticate(UserAuthDTO userAuthDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(userAuthDTO.getUsername(), userAuthDTO.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsPC userDetail = (UserDetailsPC) authentication.getPrincipal();
        return new TokenResource(TokenUtils.createToken(userDetail),userDetail.getId());
    }
}
