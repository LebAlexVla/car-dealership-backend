package ru.lebedev.dealership.application.contracts.car.models;

public record CarHeadInputDto(
        String userId,
        String brand,
        String model,
        String bodyType
) {}