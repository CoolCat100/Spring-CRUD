package com.example.SpringCRUD.controller;

import com.example.SpringCRUD.dto.UserDTO;
import com.example.SpringCRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/out")
    public ResponseEntity<String> logout(@RequestBody String logout) {
        SecurityContextHolder.clearContext();
        return  ResponseEntity.ok("Вы успешно вышли из системы");
    }
}
