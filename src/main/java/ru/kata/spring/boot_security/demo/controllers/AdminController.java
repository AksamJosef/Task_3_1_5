package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.EntityUserDetailsService;
import ru.kata.spring.boot_security.demo.service.RolesService;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final EntityUserDetailsService userDetailsService;
    private final RolesService rolesService;

    @Autowired
    public AdminController(UserService userService, EntityUserDetailsService userDetailsService, RolesService rolesService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.rolesService = rolesService;
    }

    @GetMapping("")
    public String getAdminPage(Model model) {
        model.addAttribute("users", userService.getUserList());
        model.addAttribute("user", userDetailsService.getCurrentUser());
        model.addAttribute("roles", rolesService.getAllRoles());
        return "admin_pages/admin";
    }

}
