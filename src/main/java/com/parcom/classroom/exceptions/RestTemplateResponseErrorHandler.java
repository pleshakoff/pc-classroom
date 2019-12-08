package com.parcom.classroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {


    @Override
    protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {

        System.out.println(response.getBody());
        super.handleError(response, statusCode);
    }
}
