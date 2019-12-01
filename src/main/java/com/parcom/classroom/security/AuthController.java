package com.parcom.classroom.security;

import com.parcom.classroom.model.user.TokenResource;
import com.parcom.classroom.model.user.User;
import com.parcom.classroom.security.dto.UserAuthDTO;
import com.parcom.classroom.security.dto.UserRegisterByGroupDTO;
import com.parcom.classroom.security.dto.UserRegisterByStudentDTO;
import com.parcom.classroom.security.dto.UserRegisterNewGroupDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/auth", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
@Api(tags="Register and authentication")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping(value = "/login")
    @ApiOperation("Get user session token")
    public TokenResource authenticate(@Valid @RequestBody UserAuthDTO userAuthDTO,
                                      BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return authService.authenticate(userAuthDTO);
    }

    @PostMapping(value = "/register/member")
    @ApiOperation("Member of the parental committee registration")
    public User registerMember(@Valid @RequestBody UserRegisterByGroupDTO userRegisterByGroupDTO,
                               BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return authService.registerByGroup(userRegisterByGroupDTO);
    }

    @PostMapping(value = "/register/parent")
    @ApiOperation("Regular parent registration")
    public User registerParent(@Valid @RequestBody UserRegisterByStudentDTO userRegisterByStudentDTO,
                               BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return authService.registerByStudent(userRegisterByStudentDTO);
    }

    @PostMapping(value = "/register/newgroup")
    @ApiOperation("Create new group")
    public User registerParent(@Valid @RequestBody UserRegisterNewGroupDTO userRegisterNewGroupDTO,
                               BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return authService.registerNewGroup(userRegisterNewGroupDTO);
    }


}
