package com.parcom.classroom.model.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserRemote {

    @Value("parcom.services.security.host")
    private String securityHost;

    @Value("parcom.services.security.port")
    private String securityPort;

    RestTemplate restTemplate = new RestTemplate();


   public User register(UserCreateDto userCreateDto){
        return null;
    }

   public void delete(Long idUser){

    }

}
