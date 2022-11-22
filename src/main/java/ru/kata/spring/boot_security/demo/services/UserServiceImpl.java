package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserRepository userRepository) {
        this.userDao = userDao;
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        System.out.println(user.get());
        return user;
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
