package com.parcom.classroom.model.group;

import com.parcom.classroom.exceptions.NotFoundParcomException;
import com.parcom.i18n.LocalizationUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@RestController
@RequestMapping(value = "/group",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
@Api(tags="Groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private  final MessageSource messageSource;



    @GetMapping
    @ApiOperation(value = "Get current group")
    public Group getCurrentGroup()  {
       return groupService.getCurrentGroup();
    }

    @GetMapping("/my")
    @ApiOperation(value = "Get list of my groups")
    public List<Group> getMyGroups()  {
        return groupService.getMyGroups();
    }




}
