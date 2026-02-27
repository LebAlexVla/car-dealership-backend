package ru.lebedev.dealership.domain.car.valueobjects;

public record CarColor(String color) {
    public CarColor {
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("Car color can't be null or blank");
        }
    }
}
