package ru.lebedev.dealership.controller.order.configuredcar.dto;

public record ConfiguredCarOrderOutputDto(
        Long id,
        Long configurationId,
        String status
) {
}