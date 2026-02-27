package ru.lebedev.dealership.domain.car.valueobjects;

public record CarModel(String name) {
    public CarModel {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Car model name can't be null or blank");
        }
    }
}
