package ru.lebedev.dealership.presentation.car.models;

public record CarHeadInputDto(
        String brand,
        String model,
        String bodyType
) {
}