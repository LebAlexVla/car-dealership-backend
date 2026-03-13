package ru.lebedev.dealership.domain.detail;

import ru.lebedev.dealership.domain.exceptions.InvalidValueObjectException;

public record DetailType(String name) {
    public DetailType {
        if (name == null || name.isBlank()) {
            throw new InvalidValueObjectException("Detail type name can't be null or blank");
        }
    }
}
