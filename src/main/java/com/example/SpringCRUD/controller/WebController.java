package com.example.SpringCRUD.controller;

import com.example.SpringCRUD.domain.User;
import com.example.SpringCRUD.service.RoleService;
import com.example.SpringCRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping()
public class WebController {
    @GetMapping("/admin")
    public String showAdmin() {
        return "adminPanel";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/user")
    public String showUser() {
        return "userPanel";
    }
    @GetMapping("/")
    public String goToLogin() {
        return "userPanel";
    }
}
