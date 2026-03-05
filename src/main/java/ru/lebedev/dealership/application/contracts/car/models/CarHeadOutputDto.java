package ru.lebedev.dealership.application.contracts.car.models;

public record CarHeadOutputDto(
        String carHeadId,
        String brand,
        String model,
        String bodyType
) {
}
