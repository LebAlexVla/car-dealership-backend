package ru.lebedev.dealership.application.exceptions;

public class DefaulterNotFoundException extends ApplicationException {
    public DefaulterNotFoundException(Long id) {
        super("Car configuration defaulter with id " + id + " not found");
    }
}