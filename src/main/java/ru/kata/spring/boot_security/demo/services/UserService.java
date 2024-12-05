package ru.kata.spring.boot_security.demo.services;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> index();


    void saveUser(User user);

    void deleteUser(int idUser);

    void updateUser(User user);


    Optional<User> findByUsername(String username);

    User getUserOfAuthentication();

}
