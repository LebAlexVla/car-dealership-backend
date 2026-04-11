package ru.lebedev.dealership.application.exceptions;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException(Long id) {
        super("User with id " + id + " not found");
    }
}