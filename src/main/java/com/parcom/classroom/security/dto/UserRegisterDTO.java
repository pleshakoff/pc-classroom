package com.parcom.classroom.security.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UserRegisterDTO {

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


    @JsonCreator
    public UserRegisterDTO(String username, String firstName, String middleName, String familyName, String email, String phone, String password, String passwordConfirm) {
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.familyName = familyName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.passwordConfirm = passwordConfirm;

    }


}