package com.parcom.classroom.model.add;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class AddDto {

    @NotNull
    @Email
    protected final String email;

    @NotNull
    protected final String password;

    @NotNull
    protected final String passwordConfirm;




}