package com.parcom.classroom.model.user;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRegisterByGroupDTO {

    private String username;
    private String firstName;
    private String middleName;
    private String familyName;
    private String email;
    private String phone;
    private String password;
    private String passwordConfirm;
    private Long groupId;

    @JsonCreator
    public UserRegisterByGroupDTO(String username, String firstName, String middleName, String familyName, String email, String phone, String password, String passwordConfirm, Long groupId) {
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.familyName = familyName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.groupId = groupId;
    }


}