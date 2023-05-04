package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RestRoleController {

    private final RolesRepository rolesRepository;

    @Autowired
    public RestRoleController(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return rolesRepository.findAll();
    }
}
