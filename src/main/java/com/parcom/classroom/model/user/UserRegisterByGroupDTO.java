package com.parcom.classroom.model.user;


import lombok.Getter;

import javax.validation.constraints.NotNull;


@Getter
public class UserRegisterByGroupDTO extends UserRegisterDTO {

    @NotNull
    private final Long groupId;

    public UserRegisterByGroupDTO(String username, String firstName, String middleName, String familyName, String email, String phone, String password, String passwordConfirm, Long groupId) {
        super(username, firstName, middleName, familyName, email, phone, password, passwordConfirm);
        this.groupId = groupId;
    }
}