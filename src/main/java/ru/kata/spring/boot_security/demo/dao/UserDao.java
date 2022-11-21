package Aaahooly.SpringBootLesson331.dao;



import Aaahooly.SpringBootLesson331.models.User;

import java.util.List;


public interface UserDao  {

    public List<User> index();

    public User show(int idUser);

    public void saveUser(User user);

    void deleteUser(int idUser);

    public void updateUser(int idUser, User user);
}
