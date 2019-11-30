package com.parcom.classroom.security;

import com.parcom.classroom.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

class UserDetailsPC implements UserDetails {

    private final String username;
    private final String password;
    private final Long id;
    private final Collection<? extends GrantedAuthority> authorities;

    UserDetailsPC(User user) {
        username= user.getUsername();
        password= user.getPassword();
        id = user.getId();

        Set<GrantedAuthority> a = new HashSet<>();
            a.add(new SimpleGrantedAuthority(user.getRole().name()));
        authorities = a;
    }

    UserDetailsPC(String username,  Long id, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = null;
        this.id = id;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
             return authorities;
    }

    String getAuthoritiesStr() {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
    }

    Long getId() {
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
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
