package com.parcom.classroom.security.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class UserRegisterDto {

    @NotNull
    protected final String username;

    @NotNull
    protected final String firstName;

    protected final String middleName;

    @NotNull
    protected final String familyName;

    @NotNull
    protected final String email;

    @NotNull
    protected final String phone;

    @NotNull
    protected final String password;

    @NotNull
    protected final String passwordConfirm;




}