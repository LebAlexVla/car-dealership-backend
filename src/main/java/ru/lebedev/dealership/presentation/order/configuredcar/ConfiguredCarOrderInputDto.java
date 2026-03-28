package ru.lebedev.dealership.presentation.order.configuredcar;

public record ConfiguredCarOrderInputDto(
        Long clientId,
        Long carVersionId,
        Long configurationId
) {
}
