package com.parcom.classroom.model.user;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/auth", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
@Api(tags="Register and authentication")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;



//    @PostMapping(value = "/register/member")
//    @ApiOperation("Member of the parental committee registration")
//    public User registerMember(@Valid @RequestBody UserRegisterByGroupDto userRegisterByGroupDTO,
//                               BindingResult bindingResult) throws BindException {
//        if (bindingResult.hasErrors()) {
//            throw new BindException(bindingResult);
//        }
//        return registerService.registerByGroup(userRegisterByGroupDTO);
//    }
//
//    @PostMapping(value = "/register/parent")
//    @ApiOperation("Regular parent registration")
//    public User registerParent(@Valid @RequestBody UserRegisterByStudentDto userRegisterByStudentDTO,
//                               BindingResult bindingResult) throws BindException {
//        if (bindingResult.hasErrors()) {
//            throw new BindException(bindingResult);
//        }
//        return registerService.registerByStudent(userRegisterByStudentDTO);
//    }
//
//    @PostMapping(value = "/register/newgroup")
//    @ApiOperation("Create new group")
//    public User registerParent(@Valid @RequestBody UserRegisterNewGroupDto userRegisterNewGroupDTO,
//                               BindingResult bindingResult) throws BindException {
//        if (bindingResult.hasErrors()) {
//            throw new BindException(bindingResult);
//        }
//        return registerService.registerNewGroup(userRegisterNewGroupDTO);
//    }
//

}
