package com.example.SpringCRUD.controller;

import com.example.SpringCRUD.domain.User;
import com.example.SpringCRUD.service.RoleService;
import com.example.SpringCRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
public class WebController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public WebController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping("/admin")
    public String showAdmin() {
        return "adminPanel";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("adminRole", userService.isAdmin(id));
        model.addAttribute("userRole", userService.isRoleUser(id));
        return "admin/edit";
    }
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id,
//                         @ModelAttribute("password") String pass,
//                         @ModelAttribute("adminRole") String isAdmin, @ModelAttribute("userRole") String isUser) {
//        userService.checkRoleAdmin(id, isAdmin);
//        userService.checkRoleUser(id, isUser);
//        userService.updateUser(id, user, pass);
//        return "redirect:/admin/users";
//    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
//    @GetMapping("/new")
//    public String newUser(Model model) {
//        model.addAttribute("user", new User());
//        return "new";
//    }
    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }
    @GetMapping("/user")
    public String showUser(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "userPanel";
    }
}
