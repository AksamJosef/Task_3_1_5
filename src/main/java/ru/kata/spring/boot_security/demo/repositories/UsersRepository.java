package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    @Override
    @EntityGraph(attributePaths = {"roles"})
    List<User> findAll();

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles  WHERE u.username = :username")
    Optional<User> findByUsername(String username);

}
