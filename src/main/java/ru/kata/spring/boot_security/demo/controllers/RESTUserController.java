package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.EntityUserDetailsService;

@RestController
@RequestMapping("/api")
public class RESTUserController {

    private final EntityUserDetailsService userDetailsService;

    @Autowired
    public RESTUserController(EntityUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/user")
    public UserDTO getCurrentUser() {
        return new UserDTO(userDetailsService.getCurrentUser());
    }
}
