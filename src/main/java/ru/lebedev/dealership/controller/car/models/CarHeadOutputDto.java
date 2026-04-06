package ru.lebedev.dealership.controller.car.models;

public record CarHeadOutputDto(
        Long id,
        String brand,
        String model,
        String bodyType
) {
}
