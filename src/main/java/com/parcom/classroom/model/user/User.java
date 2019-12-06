package com.parcom.classroom.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@Table(name = "Users")
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



}