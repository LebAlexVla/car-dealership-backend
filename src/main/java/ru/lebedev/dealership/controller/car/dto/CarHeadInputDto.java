package ru.lebedev.dealership.controller.car.dto;

public record CarHeadInputDto(
        String brand,
        String model,
        String bodyType
) {
}