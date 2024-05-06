package com.example.SpringCRUD.controller;

import com.example.SpringCRUD.domain.User;
import com.example.SpringCRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping()
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping()
    public String hello() {
        return "hello";
    }

    @GetMapping("/user")
    public String showUser(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "user";
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
    @PostMapping("/")
    public String create(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/user";
    }

    @PatchMapping("/user/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.updateUser(id, user);
        return "redirect:/user";
    }
}
