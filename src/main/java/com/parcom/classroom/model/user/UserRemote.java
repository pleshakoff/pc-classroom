package com.parcom.classroom.model.user;

import com.parcom.classroom.exceptions.RestTemplateResponseErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserRemote {

    @Value("${parcom.services.security.host}")
    private String securityHost;

    @Value("${parcom.services.security.port}")
    private String securityPort;

    private  final RestTemplate restTemplate;
    private  final  RestTemplateResponseErrorHandler restTemplateResponseErrorHandler;

   public User register(UserCreateDto userCreateDto){

       HttpHeaders headers = new HttpHeaders();
       headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
       headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    //   restTemplate.setErrorHandler(restTemplateResponseErrorHandler);

       URI http = UriComponentsBuilder.newInstance()
               .scheme("http").host(securityHost).port(securityPort).path("/users/register").build().toUri();

       HttpEntity<UserCreateDto> requestBody = new HttpEntity<>(userCreateDto,headers);
       ResponseEntity<User> userResponseEntity = restTemplate.postForEntity(http, requestBody, User.class);
       if (userResponseEntity.getStatusCode()== HttpStatus.OK) {
           return userResponseEntity.getBody();
       }
       else
       {
           System.out.println(userResponseEntity.getBody().toString());
           throw new RuntimeException(userResponseEntity.getBody().toString());
       }

    }

   public void delete(Long idUser){

    }

}
