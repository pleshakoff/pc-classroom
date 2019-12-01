package com.parcom.classroom.security.dto;


import lombok.Getter;

import javax.validation.constraints.NotNull;


@Getter
public class UserRegisterByGroupDTO extends UserRegisterByStudentDTO {

    @NotNull
    private final Long idGroup;

    public UserRegisterByGroupDTO(String username, String firstName, String middleName, String familyName, String email, String phone, String password, String passwordConfirm, Long idStudent, @NotNull Long idGroup) {
        super(username, firstName, middleName, familyName, email, phone, password, passwordConfirm, idStudent);
        this.idGroup = idGroup;
    }
}