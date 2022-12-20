package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
@Transactional
@Service
public class RegistrationService {

    private final  PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public RegistrationService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    //При регистрации добавляем Кодирование пароля
    //Добавляем авторизацию. При регистрации каждый пользователь по умолчанию будет иметь роль юзер
    @Transactional
    public void registration(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(new Role(2));
        userRepository.save(user);
    }
}
