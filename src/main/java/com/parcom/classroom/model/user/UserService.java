package com.parcom.classroom.model.user;

import com.parcom.classroom.model.group.Group;
import com.parcom.classroom.model.group.GroupToUser;
import com.parcom.classroom.model.group.GroupToUserRepository;
import com.parcom.classroom.model.student.Student;
import com.parcom.classroom.model.student.StudentToUser;
import com.parcom.classroom.model.student.StudentToUserRepository;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String USER_NOT_FOUND = "User not found";

    private final UserRepository userRepository;
    private  final RestTemplate restTemplate;
    private  final GroupToUserRepository groupToUserRepository;
    private  final StudentToUserRepository studentToUserRepository;

    @Value("${parcom.services.security.host}")
    private String securityHost;

    @Value("${parcom.services.security.port}")
    private String securityPort;


    public User create(String email){
        return userRepository.save( User.builder().email(email).build());
    }

    public User current(){
        return userRepository.findById(UserUtils.getIdUser()).orElseThrow(()->new EntityNotFoundException(USER_NOT_FOUND) );
    }

    public void addUserToGroup(Group group, User user) {
        groupToUserRepository.save(GroupToUser.builder().group(group).user(user).build());
    }

    public void addUserToStudent(Student student, User user) {
        studentToUserRepository.save(StudentToUser.builder().student(student).user(user).build());
    }



    public User getById(@NotNull Long id) {
        if ((UserUtils.getRole().equals(UserUtils.ROLE_PARENT))&&!UserUtils.getIdUser().equals(id))
            throw new EntityNotFoundException(USER_NOT_FOUND);
        return allInGroup().stream().filter(user -> user.getId().equals(id)).findFirst().orElseThrow(()->new EntityNotFoundException(USER_NOT_FOUND) );
    }

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    public List<User> allInGroup(){
        return groupToUserRepository.findMyGroupUsers(UserUtils.getIdGroup());
    }


    @Transactional
    User update(Long id, UserUpdateDto userUpdateDto)
    {
        if ((UserUtils.getRole().equals(UserUtils.ROLE_PARENT))&&!UserUtils.getIdUser().equals(id))
           throw new AccessDeniedException("User update forbidden");
        User user = getById(id);
        user.setPhone(userUpdateDto.getPhone());
        user.setFirstName(userUpdateDto.getFirstName());
        user.setFamilyName(userUpdateDto.getFamilyName());
        user.setMiddleName(userUpdateDto.getMiddleName());
        return userRepository.save(user);
    }


    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }

    @Transactional
    public void delete(Long id){

        if ((UserUtils.getRole().equals(UserUtils.ROLE_PARENT))&&!UserUtils.getIdUser().equals(id))
            throw new AccessDeniedException("User update forbidden");

        userRepository.deleteById(id);

        URI http = UriComponentsBuilder.newInstance()
                .scheme("http").host(securityHost).port(securityPort).path("/users/delete").queryParam("id",id).build().toUri();
        restTemplate.delete(http);
    }


    public userSecurityDto registerInSecurity(UserCreateDto userCreateDto){

        HttpHeaders headers = getHttpHeaders();
        URI http = UriComponentsBuilder.newInstance().scheme("http").host(securityHost).port(securityPort).path("/users/registerInSecurity").build().toUri();

        HttpEntity<UserCreateDto> requestBody = new HttpEntity<>(userCreateDto,headers);
        ResponseEntity<userSecurityDto> userResponseEntity = restTemplate.postForEntity(http, requestBody, userSecurityDto.class);
        if (userResponseEntity.getStatusCode()== HttpStatus.OK) {
            return userResponseEntity.getBody();
        }
        else
        {
            throw new RuntimeException(Objects.requireNonNull(userResponseEntity.getBody()).toString());
        }

    }





}
