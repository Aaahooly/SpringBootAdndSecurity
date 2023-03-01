package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        if (user.getPassword().length() == 0) {
            user.setPassword(findById(user.getId()).getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    public User getUserOfAuthentication() {
        User user = findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        return user;
    }
}
