package ru.kata.spring.boot_security.demo.services;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<User> index();

    public User show(int idUser);

    public void saveUser(User user);

    public void deleteUser(int idUser);

    public void updateUser(int idUser,User user);

    public Optional<User> findByUsername(String username);

    public User getUserOfAuthentication();
}
