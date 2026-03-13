package ru.lebedev.dealership.domain.car.vo;

import ru.lebedev.dealership.domain.exceptions.InvalidValueObjectException;

public record EnginePower(long horsepower) {
    public EnginePower {
        if (horsepower <= 0) {
            throw new InvalidValueObjectException("Power must be positive");
        }
    }
}