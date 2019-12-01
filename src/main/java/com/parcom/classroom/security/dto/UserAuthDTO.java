package com.parcom.classroom.security.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@Getter
public class UserAuthDTO {

    private final String username;

    private String password;

    @JsonCreator
    public UserAuthDTO(@JsonProperty("username") String username,
                       @JsonProperty("password") String password
)
    {
        this.username = username;
        this.password = password;
    }

}