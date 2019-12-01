package com.parcom.classroom.security;


import com.parcom.classroom.model.user.UserRepository;
import com.parcom.classroom.model.user.User;
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
       User user =repo.findUserByUsername(username);
       if (user !=null) {
           return new UserDetailsPC(user);
       }
       else
         throw  new BadCredentialsException(messageSource.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", null, LocaleContextHolder.getLocale()));
    }


}
