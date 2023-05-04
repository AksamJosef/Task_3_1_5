package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    @Override
    public void addAsUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        usersRepository.save(user);
    }

    @Override
    public List<User> getUserList() {
        return usersRepository.findAll();
    }


    @Transactional
    @Override
    public void delete(Long id) {
        usersRepository.deleteById(id);
    }


    @Transactional
    @Override
    public void update(User user) {

        User userInDB = getUserById(user.getId());

        if (!(user.getPassword().equals(userInDB.getPassword()))) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        }

        usersRepository.save(user);
    }


    @Override
    public User getUserById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }


}
