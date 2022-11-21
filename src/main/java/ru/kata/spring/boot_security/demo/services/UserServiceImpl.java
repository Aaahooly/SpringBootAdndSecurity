package Aaahooly.SpringBootLesson331.services;


import Aaahooly.SpringBootLesson331.dao.UserDao;
import Aaahooly.SpringBootLesson331.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional(rollbackFor = SQLException.class, readOnly = true)
    @Override
    public List<User> index() {
        return userDao.index();
    }

    @Override
    public User show(int idUser) {
        return userDao.show(idUser);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(int idUser) {
        userDao.deleteUser(idUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUser(int idUser, User user) {
        userDao.updateUser(idUser, user);
    }
}
