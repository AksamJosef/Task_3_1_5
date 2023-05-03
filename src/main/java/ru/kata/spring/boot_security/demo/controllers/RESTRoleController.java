package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RESTRoleController {

    private final RolesRepository rolesRepository;

    @Autowired
    public RESTRoleController(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return rolesRepository.findAll();
    }
}
