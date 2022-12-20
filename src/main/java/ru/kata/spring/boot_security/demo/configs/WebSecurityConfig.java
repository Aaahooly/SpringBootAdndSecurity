package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserDetailsService userDetailsService) {
        this.successUserHandler = successUserHandler;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().and()
                .authorizeRequests()
                .antMatchers("/admin/**", "/users/**").hasAnyRole("ADMIN")//Говорим что на страницу админа, может попасть прользователь с ролью админ
                .antMatchers("/users/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/auth", "/error", "/auth/registration").permitAll()//Сначала пускаем только на страницу логирования и ошибки
                .and()
                .formLogin().loginPage("/auth/login")//Своя форма логирования
                .loginProcessingUrl("/process_login")//вводится название адресса которому передаются данные с формы
                .successHandler(successUserHandler)// Гибкая настройка редиректа при определённых данных юзера
                .failureUrl("/auth?error")//Если аутентификация не успешна выкидываем ошибку и перенаправляем обратно на страницу логирования
                .and().logout().logoutUrl("/logout")//logout- выход из сессии и куки
                .logoutSuccessUrl("/auth");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
