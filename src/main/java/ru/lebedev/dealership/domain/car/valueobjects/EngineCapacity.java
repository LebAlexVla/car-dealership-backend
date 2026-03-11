package ru.lebedev.dealership.domain.car.valueobjects;

import ru.lebedev.dealership.domain.exceptions.InvalidValueObjectException;

public record EngineCapacity(double liters) {
    public EngineCapacity {
        if (liters <= 0.0) {
            throw new InvalidValueObjectException("Capacity must be positive");
        }
    }
}