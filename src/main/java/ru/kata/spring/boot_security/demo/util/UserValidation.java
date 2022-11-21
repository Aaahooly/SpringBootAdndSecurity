package Aaahooly.SpringSecurityApp.util;

import Aaahooly.SpringSecurityApp.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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
