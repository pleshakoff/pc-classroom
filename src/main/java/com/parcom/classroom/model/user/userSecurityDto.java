package com.parcom.classroom.model.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class userSecurityDto {

    private final Long id;
    private final String username;
    private final String role;
    private final boolean enabled;
    private final Long idGroup;


}