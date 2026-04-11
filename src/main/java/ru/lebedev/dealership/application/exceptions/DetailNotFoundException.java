package ru.lebedev.dealership.application.exceptions;

public class DetailNotFoundException extends ApplicationException {
    public DetailNotFoundException(Long id) {
        super("Detail with id " + id + " not found");
    }
}