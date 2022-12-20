package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
        System.out.println(user.get());
        return user;
    }

    @Transactional(rollbackFor = SQLException.class, readOnly = true)
    @Override
    public List<User> index() {
        return userRepository.findAll();
    }


    @Override
    public User show(int idUser) {
        return userRepository.findById(idUser).get();
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(int idUser) {
        userRepository.deleteById(idUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUser(int idUser, User user) {
        User userDb = userRepository.findById(idUser).get();
        userDb.setUsername(user.getUsername());
        userDb.setAge(user.getAge());
        userDb.setPassword(user.getPassword());
        userDb.setRoles(user.getRoles());
    }

    @Override
    public User getUserOfAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = findByUsername(name).get();
        return user;
    }
}
