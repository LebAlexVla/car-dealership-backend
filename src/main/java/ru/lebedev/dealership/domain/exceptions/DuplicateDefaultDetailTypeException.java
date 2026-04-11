package ru.lebedev.dealership.domain.exceptions;

public class DuplicateDefaultDetailTypeException extends DomainException {
    public DuplicateDefaultDetailTypeException(String message) {
        super(message);
    }
}