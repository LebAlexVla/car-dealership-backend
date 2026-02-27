package ru.lebedev.dealership.domain.car.valueobjects;

public record DetailType(String name) {
    public DetailType {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Detail type name can't be null or blank");
        }
    }
}
