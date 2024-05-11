package com.example.SpringCRUD.controller;

import com.example.SpringCRUD.domain.User;
import com.example.SpringCRUD.service.RoleService;
import com.example.SpringCRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping()
    public String showAdmin(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "admin";
    }
    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "admin/users";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("adminRole", userService.isAdmin(id));
        model.addAttribute("userRole", userService.isRoleUser(id));
        return "admin/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id,
                         @ModelAttribute("password") String pass,
                         @ModelAttribute("adminRole") String isAdmin, @ModelAttribute("userRole") String isUser) {
        System.out.println(isAdmin);
        System.out.println(isUser);
        userService.checkRoleAdmin(id, isAdmin);
        userService.checkRoleUser(id, isUser);
        userService.updateUser(id, user, pass);
        return "redirect:/admin/users";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }
    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/success";
    }
}
