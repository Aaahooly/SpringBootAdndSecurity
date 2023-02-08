package ru.kata.spring.boot_security.demo.util;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.models.User;

@Component
public class UserValidation implements Validator {

    private final UserDetailsService userDetailsService;

    public UserValidation(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //Пишем класс который будет валидироваться
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);

    }

    //Проверка если в базе есть человек с таким именем то не проходим валидацию P.S. Кривая реализация метода.
    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        try {
            userDetailsService.loadUserByUsername(user.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;
        }
        errors.rejectValue("username", "", "Человек с таким именем уже существует!");
    }
}
