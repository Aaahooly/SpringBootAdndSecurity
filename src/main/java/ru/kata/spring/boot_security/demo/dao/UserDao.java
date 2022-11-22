package ru.kata.spring.boot_security.demo.dao;



import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;


public interface UserDao  {

    public List<User> index();

    public User show(int idUser);

    public void saveUser(User user);

    void deleteUser(int idUser);

    public void updateUser(int idUser, User user);
}
