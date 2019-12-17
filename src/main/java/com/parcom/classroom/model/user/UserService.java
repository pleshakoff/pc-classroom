package com.parcom.classroom.model.user;

import com.parcom.classroom.model.group.Group;
import com.parcom.classroom.model.group.GroupToUser;
import com.parcom.classroom.model.group.GroupToUserRepository;
import com.parcom.classroom.model.student.Student;
import com.parcom.classroom.model.student.StudentToUser;
import com.parcom.classroom.model.student.StudentToUserRepository;
import com.parcom.exceptions.ForbiddenParcomException;
import com.parcom.exceptions.NotFoundParcomException;
import com.parcom.rest_template.RestTemplateUtils;
import com.parcom.security_client.Checksum;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Objects;

import static com.parcom.rest_template.RestTemplateUtils.getHttpHeaders;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String USER_NOT_FOUND = "user.not_found";
    public static final String USERS_URL = "users";

    private final UserRepository userRepository;
    private  final RestTemplate restTemplate;
    private  final GroupToUserRepository groupToUserRepository;
    private  final StudentToUserRepository studentToUserRepository;

    @Value("${parcom.services.security.host}")
    private String securityHost;

    @Value("${parcom.services.security.port}")
    private String securityPort;


    public User create(String email){
        if (userRepository.findUserByEmail(email) != null) {

            throw  new RuntimeException("user.duplicate_email");

        }

        return userRepository.save( User.builder().email(email).build());
    }

    User current(){
        return userRepository.findById(UserUtils.getIdUser()).orElseThrow(()->new NotFoundParcomException(USER_NOT_FOUND) );
    }

    public void addUserToGroup(@NotNull Group group,@NotNull User user) {
        groupToUserRepository.save(GroupToUser.builder().group(group).user(user).build());
    }

    public void addUserToStudent(@NotNull Student student, @NotNull User user) {
        studentToUserRepository.save(StudentToUser.builder().student(student).user(user).build());
    }


    User getById(@NotNull Long id) {
        if ((UserUtils.getRole().equals(UserUtils.ROLE_PARENT))&&!UserUtils.getIdUser().equals(id))
            throw new EntityNotFoundException(USER_NOT_FOUND);
        return allInGroup().stream().filter(user -> user.getId().equals(id)).findFirst().orElseThrow(()->new NotFoundParcomException(USER_NOT_FOUND) );
    }

    List<User> allInGroup(){
        return groupToUserRepository.findMyGroupUsers(UserUtils.getIdGroup());
    }


    @Transactional
    User update(Long id, UserUpdateDto userUpdateDto)
    {
        if ((UserUtils.getRole().equals(UserUtils.ROLE_PARENT))&&!UserUtils.getIdUser().equals(id))
           throw new ForbiddenParcomException();
        User user = getById(id);
        user.setPhone(userUpdateDto.getPhone());
        user.setFirstName(userUpdateDto.getFirstName());
        user.setFamilyName(userUpdateDto.getFamilyName());
        user.setMiddleName(userUpdateDto.getMiddleName());
        return userRepository.save(user);
    }


    @Transactional
    public void delete(Long id){

        if ((UserUtils.getRole().equals(UserUtils.ROLE_PARENT))&&!UserUtils.getIdUser().equals(id))
            throw new ForbiddenParcomException();

        User user = getById(id);
        groupToUserRepository.deleteAllByUser(user);
        studentToUserRepository.deleteAllByUser(user);
        userRepository.deleteById(id);

        URI url = UriComponentsBuilder.newInstance()
              .scheme(RestTemplateUtils.scheme).host(securityHost).port(securityPort).path("/" + USERS_URL + "/").path(id.toString()).build().toUri();


        HttpHeaders httpHeaders = getHttpHeaders();
        httpHeaders.set(Checksum.CHECKSUM,Checksum.createChecksum(id));
        restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity(httpHeaders), String.class);

    }


    public void registerInSecurity(UserCreateDto userCreateDto){
       URI http = UriComponentsBuilder.newInstance().scheme(RestTemplateUtils.scheme).host(securityHost).port(securityPort).path("/" + USERS_URL + "/register").build().toUri();

        HttpHeaders httpHeaders = getHttpHeaders();
        httpHeaders.set(Checksum.CHECKSUM,Checksum.createChecksum(userCreateDto.getId()));

        HttpEntity<UserCreateDto> requestBody = new HttpEntity<>(userCreateDto, httpHeaders);
        ResponseEntity<userSecurityResponseDto> userResponseEntity = restTemplate.postForEntity(http, requestBody, userSecurityResponseDto.class);
        if (userResponseEntity.getStatusCode()== HttpStatus.OK) {
            userResponseEntity.getBody();
        }
        else
        {
            throw new RuntimeException(Objects.requireNonNull(userResponseEntity.getBody()).toString());
        }

    }





}
