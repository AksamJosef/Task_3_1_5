package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.exceptions.DuplicateEmailException;
import ru.kata.spring.boot_security.demo.validators.UsernameValidator;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.exceptions.NoSuchUserException;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/api/admin/")
public class RestAdminController {

    private final UserService userService;
    private final UsernameValidator usernameValidator;

    @Autowired
    public RestAdminController(UserService userService, UsernameValidator usernameValidator) {
        this.userService = userService;
        this.usernameValidator = usernameValidator;
    }


    @GetMapping("users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userList = userService.getUserList()
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        User userById = userService.getUserById(id);
        if (userById == null)
            throw new NoSuchUserException("There are no such user with ID = " + id);

        return new ResponseEntity<>(new UserDTO(userById), HttpStatus.OK);
    }


    @PostMapping("/users")
    public ResponseEntity<HttpStatus> addNewUser(@RequestBody @Valid UserDTO newUser,
                                                 BindingResult bindingResult) {

        usernameValidator.validate(new User(newUser), bindingResult);

        if (bindingResult.hasErrors()) {
            throw new DuplicateEmailException("There are already exists user with username: "
                    + newUser.getUsername());
        }

        userService.addAsUser(new User(newUser));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/users")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid UserDTO userUpdated,
                                                 BindingResult bindingResult) {

        usernameValidator.validate(new User(userUpdated), bindingResult);

        if (bindingResult.hasErrors()) {
            throw new DuplicateEmailException("There are already exists user with username: "
                    + userUpdated.getUsername());
        }
        userService.update(new User(userUpdated));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping ("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
