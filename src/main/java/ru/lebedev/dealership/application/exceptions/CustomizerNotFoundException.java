package ru.lebedev.dealership.application.exceptions;

public class CustomizerNotFoundException extends ApplicationException {
    public CustomizerNotFoundException(Long id) {
        super("Car configuration customizer with id " + id + " not found");
    }
}