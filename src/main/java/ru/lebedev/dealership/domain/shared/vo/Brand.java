package ru.lebedev.dealership.domain.shared.vo;

import ru.lebedev.dealership.domain.exceptions.InvalidValueObjectException;

public record Brand(String name) {
    public Brand {
        if (name == null || name.isBlank()) {
            throw new InvalidValueObjectException("Brand name can't be null or blank");
        }
    }
}
