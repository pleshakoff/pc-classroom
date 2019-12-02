package com.parcom.classroom.model.student;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class StudentDTO {
    @NotNull
    private String firstName;
    private String middleName;
    @NotNull
    private String familyName;
    private LocalDate birthDay;
}
