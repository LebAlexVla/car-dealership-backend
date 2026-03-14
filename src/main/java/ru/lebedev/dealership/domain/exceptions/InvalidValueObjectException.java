package ru.lebedev.dealership.domain.exceptions;

public class InvalidValueObjectException extends DomainException {
    public InvalidValueObjectException(String message) {
        super(message);
    }
}
