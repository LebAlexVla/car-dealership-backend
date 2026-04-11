package ru.lebedev.dealership.application.exceptions;

public class CarVersionNotAvailableForTestDrive extends ApplicationException {
    public CarVersionNotAvailableForTestDrive(Long id) {
        super("Car version with id " + id + " is not available for test drive");
    }
}