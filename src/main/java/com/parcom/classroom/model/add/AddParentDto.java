package com.parcom.classroom.model.add;


import lombok.Getter;

import javax.validation.constraints.NotNull;


@Getter
public class AddParentDto extends AddDto {


    protected final Long idStudent;


    public AddParentDto(@NotNull String email, @NotNull String password, @NotNull String passwordConfirm, Long idStudent) {
        super(email, password, passwordConfirm);
        this.idStudent = idStudent;
    }
}