package ru.lebedev.dealership.application.exceptions;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException(Long id) {
        super("User with id " + id + " not found");
    }

    public UserNotFoundException(String id) {
        super("User with keycloak id " + id + " not found");
    }
}