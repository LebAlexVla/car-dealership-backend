package ru.lebedev.dealership.application.exceptions;

public abstract class ApplicationException extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }
}
