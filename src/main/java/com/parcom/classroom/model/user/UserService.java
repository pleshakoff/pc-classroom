package com.parcom.classroom.model.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Collections;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private  final RestTemplate restTemplate;

    @Value("${parcom.services.security.host}")
    private String securityHost;

    @Value("${parcom.services.security.port}")
    private String securityPort;


    public User getById(@NotNull Long idUser) {
        return userRepository.findById(idUser).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public User create(String email){

        return userRepository.save( User.builder().email(email).build());
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }

    public userSecurityDto register(UserCreateDto userCreateDto){

        HttpHeaders headers = getHttpHeaders();
        URI http = UriComponentsBuilder.newInstance().scheme("http").host(securityHost).port(securityPort).path("/users/register").build().toUri();

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

    public void delete(Long idUser){
        URI http = UriComponentsBuilder.newInstance()
                .scheme("http").host(securityHost).port(securityPort).path("/users/delete").queryParam("id",idUser).build().toUri();
        restTemplate.delete(http);
    }






}
