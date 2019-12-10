package com.parcom.classroom.services.add;


import lombok.Getter;

import javax.validation.constraints.NotNull;


@Getter
public class AddParentDto extends AddDto {


    protected final Long idStudent;


    AddParentDto(@NotNull String email, @NotNull String password, @NotNull String passwordConfirm, Long idStudent) {
        super(email, password, passwordConfirm);
        this.idStudent = idStudent;
    }
}