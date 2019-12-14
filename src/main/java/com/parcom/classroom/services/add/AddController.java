package com.parcom.classroom.services.add;

import com.parcom.classroom.model.user.User;
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
@RequestMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
@Api(tags="Add to parents committee")
@RequiredArgsConstructor
public class AddController {

    private final AddService addService;

    @PostMapping(value = "/member")
    @ApiOperation("Add member of the parental committee")
    public User registerMember(@Valid @RequestBody AddMemberDto addMemberDTO,
                                          BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return addService.registerByGroup(addMemberDTO);
    }

    @PostMapping(value = "/parent")
    @ApiOperation("Add regular parent")
    public User registerParent(@Valid @RequestBody AddParentDto addParentDTO,
                                          BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return addService.registerByStudent(addParentDTO);
    }

    @PostMapping(value = "/group")
    @ApiOperation("Add new group")
    public User registerParent(@Valid @RequestBody AddGroupDto addGroupDTO,
                               BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return addService.registerNewGroup(addGroupDTO);
    }


}
