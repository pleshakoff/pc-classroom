package com.parcom.classroom.security.dto;


import lombok.Getter;

import javax.validation.constraints.NotNull;


@Getter
public class UserRegisterByStudentDto extends UserRegisterDto {

    @NotNull
    protected final Long idStudent;

    public UserRegisterByStudentDto(String username, String firstName, String middleName, String familyName, String email, String phone, String password, String passwordConfirm, Long idStudent) {
        super(username, firstName, middleName, familyName, email, phone, password, passwordConfirm);
        this.idStudent = idStudent;
    }
}