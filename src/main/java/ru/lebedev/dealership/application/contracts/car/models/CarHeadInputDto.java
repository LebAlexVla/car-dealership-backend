package ru.lebedev.dealership.application.contracts.car.models;

public record CarHeadInputDto(
        String brand,
        String model,
        String bodyType
) {
}