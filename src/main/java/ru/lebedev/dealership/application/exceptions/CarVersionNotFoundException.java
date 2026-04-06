package ru.lebedev.dealership.application.exceptions;

public class CarVersionNotFoundException extends ApplicationException {
    public CarVersionNotFoundException(Long id) {
        super("Car version with id " + id + " not found");
    }
}
