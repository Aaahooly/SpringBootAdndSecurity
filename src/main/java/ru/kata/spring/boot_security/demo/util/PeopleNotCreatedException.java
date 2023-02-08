package ru.kata.spring.boot_security.demo.util;

public class PeopleNotCreatedException extends RuntimeException{

    public PeopleNotCreatedException(String msg) {
        super(msg);
    }
}
