package com.example.SpringCRUD.controller;

import com.example.SpringCRUD.domain.User;
import com.example.SpringCRUD.dto.UserDTO;
import com.example.SpringCRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/registration", produces = "application/json")
    public String create(@RequestBody UserDTO user) {
        return userService.addUserDto(user) ? "{\"status\":\"success\"}" : "{\"status\":\"errorLogin\"}";
    }
}
