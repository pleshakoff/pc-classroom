package com.parcom.classroom.model.user;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@RequiredArgsConstructor
public class UserCreateDto {

    @NotNull
    private final Long id;

    @NotNull
    @Email
    private final String email;

    @NotNull
    private final String password;

    @NotNull
    protected final String passwordConfirm;

    @NotNull
    private final String role;

    @NotNull
    private final Long idGroup;

}

