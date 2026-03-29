package ru.lebedev.dealership.domain.exceptions;

public class NoSuchOptionalDetailException extends DomainException {
    public NoSuchOptionalDetailException(String type, String name) {
        super("There is no optional detail " + name + " with type " + type);
    }
}