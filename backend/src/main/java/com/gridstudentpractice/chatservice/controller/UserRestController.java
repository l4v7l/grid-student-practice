package com.gridstudentpractice.chatservice.controller;

import com.gridstudentpractice.chatservice.model.UserDto;
import com.gridstudentpractice.chatservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userLogin}")
    public UserDto getUserByLogin(@PathVariable String userLogin) {
        return userService.getUserByLogin(userLogin);
    }

    @PostMapping
    public void addUser(@Valid @RequestBody UserDto userDto) {
        userService.addUser(userDto);
    }

    @PostMapping("/add-role/{roleId}/{userId}")
    public void addRoleToUser(@PathVariable("roleId") int roleId, @PathVariable("userId") int userId) {
        userService.addRoleToUser(roleId, userId);
    }

    @PutMapping
    public void updateUserLoginAndPassword(@Valid @RequestBody UserDto userDto) {
        userService.updateUserLoginAndPassword(userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable int id) {
        userService.deleteUserById(id);
    }
}
