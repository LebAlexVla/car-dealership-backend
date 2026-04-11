package ru.lebedev.dealership.application.exceptions;

public class CarHeadNotFoundException extends ApplicationException {
    public CarHeadNotFoundException(Long id) {
        super("Car Head with id " + id + " not found");
    }
}