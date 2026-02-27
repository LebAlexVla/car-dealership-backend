package ru.lebedev.dealership.domain.car.valueobjects;

public record EnginePower(long horsepower) {
    public EnginePower {
        if (horsepower <= 0) {
            throw new IllegalArgumentException("Power must be positive");
        }
    }
}
