package ru.lebedev.dealership.presentation.car.models;

public record CarHeadOutputDto(
        Long carHeadId,
        String brand,
        String model,
        String bodyType
) {
}
