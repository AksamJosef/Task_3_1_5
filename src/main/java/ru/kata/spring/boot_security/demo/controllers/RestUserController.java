package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.service.EntityUserDetailsService;

@RestController
@RequestMapping("/api")
public class RestUserController {

    private final EntityUserDetailsService userDetailsService;

    @Autowired
    public RestUserController(EntityUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return new ResponseEntity<>(new UserDTO(userDetailsService.getCurrentUser()), HttpStatus.OK);
    }
}
