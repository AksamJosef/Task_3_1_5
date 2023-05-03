package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
    }


    @Transactional
    @Override
    public void addAsUser(User user) {
        if (user.getRoles() == null) {
            user.addRole(rolesRepository.getById(1));
        } else {
            user.setRoles(user.getRoles());
        }
        usersRepository.saveAndFlush(user);
    }

    @Override
    public List<User> getUserList() {
        return usersRepository.findAll();
    }


    @Transactional
    @Override
    public void delete(int id) {
        usersRepository.deleteById(Math.toIntExact(id));
    }


    @Transactional
    @Override
    public void update(User user) {
        usersRepository.save(user);
    }


    @Override
    public User getUserById(int id) {
        return usersRepository.findById(Math.toIntExact(id)).orElse(null);
    }


}
