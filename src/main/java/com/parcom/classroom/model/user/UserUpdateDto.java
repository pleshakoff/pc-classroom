package com.parcom.classroom.model.user;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class UserUpdateDto {

    private final String firstName;
    private final String middleName;
    private final String familyName;
    private final String phone;

}