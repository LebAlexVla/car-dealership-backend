package ru.lebedev.dealership.controller.car.dto;

public record EngineDto(
        String fuelType,
        Long power,
        double capacity
) {
}