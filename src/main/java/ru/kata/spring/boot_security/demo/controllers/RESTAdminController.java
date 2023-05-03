package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.exceptions.NoSuchUserException;
import ru.kata.spring.boot_security.demo.exceptions.UserIncorrectID;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/")
public class RESTAdminController {

    private final UserService userService;

    @Autowired
    public RESTAdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("users")
    public List<UserDTO> getAllUsers() {
        return userService.getUserList()
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("users/{id}")
    public UserDTO getUser(@PathVariable int id) {
        User userById = userService.getUserById(id);
        if (userById == null)
            throw new NoSuchUserException("There are no such user with ID = " + id);

        return new UserDTO(userById);
    }


    @PostMapping("/users")
    public ResponseEntity<HttpStatus> addNewUser(@RequestBody UserDTO newUser) {
        userService.addAsUser(new User(newUser));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/users")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody UserDTO userUpdated) {
        userService.update(new User(userUpdated));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping ("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}