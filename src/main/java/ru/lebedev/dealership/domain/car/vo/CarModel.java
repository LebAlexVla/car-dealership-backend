package ru.lebedev.dealership.domain.car.vo;

import ru.lebedev.dealership.domain.exceptions.InvalidValueObjectException;

public record CarModel(String name) {
    public CarModel {
        if (name == null || name.isBlank()) {
            throw new InvalidValueObjectException("Car model name can't be null or blank");
        }
    }
}