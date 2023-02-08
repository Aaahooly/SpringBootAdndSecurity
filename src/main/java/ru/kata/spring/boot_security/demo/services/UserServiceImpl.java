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
import java.util.Timer;
import java.util.TimerTask;


@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(rollbackFor = SQLException.class, readOnly = true)
    @Override
    public List<User> index() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return userRepository.findById(id).orElse(new User());
    }

    @Override
    public void debugAdTable() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Т");
            }
        };
        Timer timer= new Timer();
        timer.schedule(timerTask,2000);
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
    public void updateUser(User user) {
        User userDb = userRepository.findById(user.getId()).get();
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
