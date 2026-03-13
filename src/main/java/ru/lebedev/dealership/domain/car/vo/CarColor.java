package ru.lebedev.dealership.domain.car.vo;

import ru.lebedev.dealership.domain.exceptions.InvalidValueObjectException;

public record CarColor(String color) {
    public CarColor {
        if (color == null || color.isBlank()) {
            throw new InvalidValueObjectException("Car color can't be null or blank");
        }
    }
}