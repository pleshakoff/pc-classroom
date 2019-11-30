package com.parcom.classroom.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class UserDetailsServiceDB implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    private final MessageSource messageSource;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Users users =repo.findUserByUsername(username);
       if (users !=null) {
           return new UserDetailsPC(users);
       }
       else
         throw  new BadCredentialsException(messageSource.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", null, LocaleContextHolder.getLocale()));
    }
}
