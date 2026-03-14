package ru.lebedev.dealership.domain.exceptions;

public class WrongOptionalDetailException extends DomainException {
    public WrongOptionalDetailException(String message) {
        super(message);
    }
}
