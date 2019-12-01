package com.parcom.classroom.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserUtilsImpl  implements UserUtils{


    private Long getPrincipal(Function<UserDetailsPC,Long> getFromPrincipal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return  null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof String && principal.equals("anonymousUser")) {
            return null;
        }
        else
        {
            return getFromPrincipal.apply((UserDetailsPC)authentication.getPrincipal());
        }
    }



    @Override
    public Long getUserId() {

        return getPrincipal(UserDetailsPC::getId);
    }

    @Override
    public Long getGroupId() {

        return getPrincipal(UserDetailsPC::getIdGroup);

    }

    @Override
    public Long getStudentId() {
        return getPrincipal(UserDetailsPC::getIdStudent);

    }
}
