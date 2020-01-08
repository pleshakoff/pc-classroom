package com.parcom.classroom.model.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/users",produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(tags="Users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping
    @ApiOperation(value = "Get current user")
    public User current()  {
       return userService.current();
    }


    @GetMapping("/all")
    @ApiOperation(value = "Get all users in group")
    public List<User> getAllUsers()
    {
        return userService.allInGroup();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get user by ID")
    public User getMyUser(@PathVariable Long id)  {
        return userService.getById(id);
    }



    @PutMapping("/{id}")
    @ApiOperation(value = "Update user")
    public User update(@PathVariable Long id,@Valid @RequestBody UserUpdateDto userUpdateDto,
                          BindingResult bindingResult) throws BindException
    {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return userService.update(id,userUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete user")
    public void delete(@PathVariable Long id)
    {
        userService.delete(id);
    }




}
