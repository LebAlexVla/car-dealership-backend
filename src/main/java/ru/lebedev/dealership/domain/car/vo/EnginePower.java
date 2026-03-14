package ru.lebedev.dealership.domain.car.vo;

import ru.lebedev.dealership.domain.exceptions.InvalidValueObjectException;

public record EnginePower(Long horsepower) {
    public EnginePower {
        if (horsepower <= 0L) {
            throw new InvalidValueObjectException("Power must be positive");
        }
    }
}