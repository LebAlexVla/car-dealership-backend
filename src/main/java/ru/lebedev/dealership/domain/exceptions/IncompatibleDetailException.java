package ru.lebedev.dealership.domain.exceptions;

public class IncompatibleDetailException extends DomainException{
    public IncompatibleDetailException(String message) {
        super(message);
    }
}
