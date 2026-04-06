package ru.lebedev.dealership.controller.car.dto;

public record CarHeadOutputDto(
        Long id,
        String brand,
        String model,
        String bodyType
) {
}
