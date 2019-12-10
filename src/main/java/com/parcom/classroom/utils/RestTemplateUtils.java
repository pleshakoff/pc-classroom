package com.parcom.classroom.utils;

import com.parcom.security_client.UserUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;

public class RestTemplateUtils {

    public static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set(UserUtils.X_AUTH_TOKEN, UserUtils.getToken());
        return headers;
    }


    public static HttpEntity getHttpEntity() {
       return new HttpEntity(getHttpHeaders());
    }

    public final static String scheme = "http";


}
