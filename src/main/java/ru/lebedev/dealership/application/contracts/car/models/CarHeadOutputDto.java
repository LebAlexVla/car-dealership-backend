package ru.lebedev.dealership.application.contracts.car.models;

public record CarHeadOutputDto(
        Long carHeadId,
        String brand,
        String model,
        String bodyType
) {
}
