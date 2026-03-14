package ru.lebedev.dealership.application.contracts.configurationorder.models;

public record ConfigurationOrderInputDto(
        long clientId,
        long carVersionId,
        long configurationId
) {
}
