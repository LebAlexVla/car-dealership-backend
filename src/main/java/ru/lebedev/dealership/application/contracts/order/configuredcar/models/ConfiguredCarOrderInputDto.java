package ru.lebedev.dealership.application.contracts.order.configuredcar.models;

public record ConfiguredCarOrderInputDto(
        long clientId,
        long carVersionId,
        long configurationId
) {
}
