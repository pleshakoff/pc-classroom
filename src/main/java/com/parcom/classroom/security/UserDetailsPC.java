package com.parcom.classroom.security;

import com.parcom.classroom.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsPC implements UserDetails {

    private final String username;
    private final String password;
    private final Long id;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
    private final Long idGroup;


    UserDetailsPC(User user) {
        username= user.getUsername();
        password= user.getPassword();
        id = user.getId();

        Set<GrantedAuthority> a = new HashSet<>();
            a.add(new SimpleGrantedAuthority(user.getRole().name()));
        authorities = a;
        enabled = user.isEnabled();
        idGroup = user.getGroup().getId();
     }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
             return authorities;
    }

    String getAuthoritiesStr() {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Long getIdGroup() {
        return idGroup;
    }


}
