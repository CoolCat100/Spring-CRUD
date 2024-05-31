package com.example.SpringCRUD.controller;

import com.example.SpringCRUD.domain.User;
import com.example.SpringCRUD.dto.UserDTO;
import com.example.SpringCRUD.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }
    @RequestMapping(method = RequestMethod.GET, value = "/get_user")
    public User getUser() {
        return userService.getCurrentUser();
    }
    @RequestMapping(method = RequestMethod.GET, value = "/get_users")
    public List<User> showUsers() {
        return userService.getUsers();
    }
    @RequestMapping(method = RequestMethod.POST, value = "/admin", produces = "application/json")
    public String create(@RequestBody UserDTO user) {
        return userService.addUserDto(user) ? "{\"status\":\"success\"}" : "{\"status\":\"errorLogin\"}";
    }
    @RequestMapping(method = RequestMethod.GET, value = "/edit/{id}")
    public User findEdit(@PathVariable("id") int id) {
        return userService.getUser(id);
    }
    @RequestMapping(method = RequestMethod.POST, value = "/admin/edit", produces = "application/json")
    public String update(@RequestBody UserDTO user) {
        userService.updateUser(user);
        return "{\"status\":\"success\"}";
    }
    @RequestMapping(method = RequestMethod.POST, value = "/admin/delete", produces = "application/json")
    public String delete(@RequestBody UserDTO user) {
        userService.deleteUser(user);
        return "{\"status\":\"success\"}";
    }
}
