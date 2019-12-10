package com.parcom.classroom.services.add;


import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
class AddGroupDto extends AddDto {



    private final Long idSchool;

    private final String nameSchool;

    private final String nameGroup;

    public AddGroupDto(@NotNull String email, @NotNull String password, @NotNull String passwordConfirm, Long idSchool, String nameSchool, String nameGroup) {
        super(email, password, passwordConfirm);
        this.idSchool = idSchool;
        this.nameSchool = nameSchool;
        this.nameGroup = nameGroup;
    }

}