package ru.lebedev.dealership.controller.car.models;

public record CarHeadInputDto(
        String brand,
        String model,
        String bodyType
) {
}