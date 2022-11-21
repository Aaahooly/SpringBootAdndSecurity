package Aaahooly.SpringBootLesson331.services;

import Aaahooly.SpringBootLesson331.models.User;

import java.util.List;

public interface UserService {
    public List<User> index();

    public User show(int idUser);

    public void saveUser(User user);

    public void deleteUser(int idUser);

    public void updateUser(int idUser,User user);
}
