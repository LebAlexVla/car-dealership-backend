package ru.lebedev.dealership.domain.exceptions;

public class WrongRequiredDetailException extends DomainException {
    public WrongRequiredDetailException(String message) {
        super(message);
    }
}
