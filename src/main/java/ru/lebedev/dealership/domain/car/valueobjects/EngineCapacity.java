package ru.lebedev.dealership.domain.car.valueobjects;

public record EngineCapacity(long liters) {
    public EngineCapacity {
        if (liters <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
    }
}
