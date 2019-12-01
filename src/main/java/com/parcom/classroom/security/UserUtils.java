package com.parcom.classroom.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.function.Function;


public class UserUtils {

    private static Long getPrincipal(Function<UserDetailsPC,Long> getFromPrincipal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AccessDeniedException("Access denied");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof String && principal.equals("anonymousUser")) {
            throw new AccessDeniedException("Access denied");
        }
        else
        {
            return getFromPrincipal.apply((UserDetailsPC)authentication.getPrincipal());
        }
    }


    public static Long getUserId() {

        return getPrincipal(UserDetailsPC::getId);
    }


    public static Long getGroupId() {

        return getPrincipal(UserDetailsPC::getIdGroup);

    }


    public static Long getStudentId() {
        return getPrincipal(UserDetailsPC::getIdStudent);

    }
}