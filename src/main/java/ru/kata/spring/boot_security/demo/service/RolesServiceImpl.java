package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;

import java.util.HashSet;
import java.util.Set;


@Service
public class RolesServiceImpl implements RolesService{

    private final RolesRepository rolesRepository;

    @Autowired
    public RolesServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }


    @Override
    public Set<Role> getAllRoles() {
        return new HashSet<>(rolesRepository.findAll());
    }
}
