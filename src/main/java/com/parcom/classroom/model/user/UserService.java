package com.parcom.classroom.model.user;

import com.parcom.classroom.security.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;


@Service
@RequiredArgsConstructor
public class UserService {

     private final UserRepository userRepository;

     public  User getCurrentUser(){
        return userRepository.findById(UserUtils.getIdUser()).orElseThrow(EntityExistsException::new);
     }

}
