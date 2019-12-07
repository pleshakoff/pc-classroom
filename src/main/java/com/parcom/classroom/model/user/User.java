package com.parcom.classroom.model.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class User {

    private Long id;
    private String username;
    private String firstName;
    private String middleName;
    private String familyName;
    private String email;
    private String phone;
    private String password;
    private String role;
    private boolean enabled;
    private Long idGroup;

    @JsonCreator
    public User(Long id, String username, String firstName, String middleName, String familyName, String email, String phone, String password, String role, boolean enabled, Long idGroup) {

        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.familyName = familyName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
        this.idGroup = idGroup;
    }


}