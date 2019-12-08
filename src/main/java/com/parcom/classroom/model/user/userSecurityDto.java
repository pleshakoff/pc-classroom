package com.parcom.classroom.model.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class userSecurityDto {

    private Long id;
    private String username;
    private String role;
    private boolean enabled;
    private Long idGroup;

    @JsonCreator
    public userSecurityDto(Long id, String username, String role, boolean enabled, Long idGroup) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.enabled = enabled;
        this.idGroup = idGroup;
    }
}