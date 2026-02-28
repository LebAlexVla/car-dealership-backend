package ru.lebedev.dealership.application.contracts.car.models;

public record CarHeadFilterDto(
        String brand,
        String carModel,
        String bodyType
) {}
