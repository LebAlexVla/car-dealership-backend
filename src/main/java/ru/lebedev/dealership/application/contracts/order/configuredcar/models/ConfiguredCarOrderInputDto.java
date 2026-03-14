package ru.lebedev.dealership.application.contracts.order.configuredcar.models;

public record ConfiguredCarOrderInputDto(
        Long clientId,
        Long carVersionId,
        Long configurationId
) {
}
