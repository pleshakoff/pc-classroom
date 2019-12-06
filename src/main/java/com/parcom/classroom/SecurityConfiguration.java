package com.parcom.classroom;


import com.parcom.security_client.AuthenticationTokenProcessingFilter;
import com.parcom.security_client.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Created by zey on 26.04.2017.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Autowired
    AuthenticationTokenProcessingFilter authenticationTokenProcessingFilter;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/webjars/springfox-swagger-ui/**",
                "/swagger-ui.html/**",
                "/swagger-resources/**",
                "/v2/api-docs",


                "/auth/**", "/health").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint)
                .and()
                .addFilterBefore(authenticationTokenProcessingFilter, UsernamePasswordAuthenticationFilter.class);
    }


















}