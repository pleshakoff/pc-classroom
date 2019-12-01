package com.parcom.classroom.model.group;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/groups",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
@Api(tags="Groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;


    @GetMapping
    @ApiOperation(value = "Get current groups")
    public Group getCurrentGroup()  {

       return groupService.getCurrentGroup();
    }



}
