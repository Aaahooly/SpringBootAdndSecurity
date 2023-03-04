package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.PeopleErrorResponse;
import ru.kata.spring.boot_security.demo.util.PeopleNotCreatedException;
import ru.kata.spring.boot_security.demo.util.PeopleNotFoundException;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/people")
public class PeopleRestController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public PeopleRestController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping()
    public List<User> findAll() {
        return userService.index();//jackson конвертирует объекты в json
    }

    @GetMapping("/authentication")
    public User getUserOfAuthentication() {
        System.out.println("Получили авторизорованного  пользователя");
        return userService.getUserOfAuthentication();
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid User user,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PeopleNotCreatedException(errorMsg.toString());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        //отправляем http с пустым телом и со статусом 200
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping()
    public ResponseEntity<HttpStatus> updates(@RequestBody @Valid User user) {
        userService.updateUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<HttpStatus> delete(@RequestBody User user) {
        userService.deleteUser(user.getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //Этот метод предназначен для внутреннего использования
    //Метод нужен для обработки ошибок, т.к. если в json прилетит null приложение крашнется
    @ExceptionHandler
    private ResponseEntity<PeopleErrorResponse> handleException(PeopleNotFoundException e) {
        PeopleErrorResponse response = new PeopleErrorResponse("Person with this id wasn't found!",
                System.currentTimeMillis()
        );
        //В HTTP ответе - тело ответа
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);//404 статуч
    }

    @ExceptionHandler
    private ResponseEntity<PeopleErrorResponse> handleException(PeopleNotCreatedException e) {
        PeopleErrorResponse response = new PeopleErrorResponse(e.getMessage(),
                System.currentTimeMillis()
        );
        //В HTTP ответе - тело ответа
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);//404 статуч
    }
}
