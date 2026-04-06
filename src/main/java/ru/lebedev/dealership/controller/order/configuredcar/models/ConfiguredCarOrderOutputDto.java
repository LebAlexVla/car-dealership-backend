package ru.lebedev.dealership.controller.order.configuredcar.models;

public record ConfiguredCarOrderOutputDto(
        Long id,
        Long configurationId,
        String status
) {
}