package com.parcom.classroom.model.user;


import lombok.Getter;

@Getter
public class UserRegisterNewGroupDto extends UserRegisterDto {



    private final Long idSchool;

    private final String nameSchool;

    private final String nameGroup;


    public UserRegisterNewGroupDto(String username, String firstName, String middleName, String familyName, String email, String phone, String password, String passwordConfirm, Long idSchool, String nameSchool, String nameGroup) {
        super(username, firstName, middleName, familyName, email, phone, password, passwordConfirm);
        this.idSchool = idSchool;
        this.nameSchool = nameSchool;
        this.nameGroup = nameGroup;
    }
}