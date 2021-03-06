package com.parcom.classroom.services.add;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;


@Getter
class AddMemberDto extends AddParentDto {

    @NotNull
    private final Long idGroup;


    public AddMemberDto(@NotNull String email, @NotNull String password, @NotNull String passwordConfirm, Long idStudent, @NotNull Long idGroup) {
        super(email, password, passwordConfirm, idStudent);
        this.idGroup = idGroup;
    }
}