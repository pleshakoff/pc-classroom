package com.parcom.classroom.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserUpdateDto {

    private final String firstName;
    private final String middleName;
    private final String familyName;
    private final String phone;

}