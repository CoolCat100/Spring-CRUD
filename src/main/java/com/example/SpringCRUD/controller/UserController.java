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
    @GetMapping()
    public String hello() {
        return "index";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        userService.logout(request);
        return "redirect:/";
    }
    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }
    @PostMapping("/new")
    public String create(@ModelAttribute("user") User user) {
        String role = "user";
        return userService.addUser(user, role) ? "redirect:/user" : "redirect:/unsuccessful";
    }
    @GetMapping("/unsuccessful")
    public String unsuccessful() {
        return "unsuccessful";
    }

//    @PatchMapping("/user/{id}")
//    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
//        userService.updateUser(id, user);
//        return "redirect:/user";
//    }
@RequestMapping(method = RequestMethod.POST, value = "/registration", produces = "application/json")
public String create(@RequestBody UserDTO user) {
    return userService.addUserDto(user) ? "{\"status\":\"success\"}" : "{\"status\":\"errorLogin\"}";
}
}
