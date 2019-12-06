package com.parcom.classroom.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserAuthDto {

    private final String username;
    private String password;

}