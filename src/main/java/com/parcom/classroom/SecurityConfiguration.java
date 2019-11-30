package com.parcom.classroom;


import com.parcom.classroom.security.AuthenticationTokenProcessingFilter;
import com.parcom.classroom.security.UnauthorizedEntryPoint;
import com.parcom.classroom.security.UserDetailsServiceDB;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Created by zey on 26.04.2017.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    MessageSource messageSource;

    public SecurityConfiguration(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/security/**","/health","/info","/**/pushers" ).permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint())
                .and()
                .addFilterBefore(new AuthenticationTokenProcessingFilter(messageSource),  UsernamePasswordAuthenticationFilter.class)
                 ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/users/authenticate",
                                               "/users/registerTenancy",
                                               "/users/registration/confirm",
                                               "/users/remaindbyemail",
                                               "/users/remaindbylogin",
                                               "/users/resetpassword");
    }


    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
       return new UserDetailsServiceDB(messageSource);
    }

    @Bean
    UnauthorizedEntryPoint unauthorizedEntryPoint(){
       return new UnauthorizedEntryPoint(messageSource);
    }


    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }













}