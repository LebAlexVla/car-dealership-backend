package ru.lebedev.dealership.application.exceptions;

public class CarConfigurationNotFoundException extends ApplicationException {
    public CarConfigurationNotFoundException(Long id) {
        super("Car configuration with id " + id + " not found");
    }
}