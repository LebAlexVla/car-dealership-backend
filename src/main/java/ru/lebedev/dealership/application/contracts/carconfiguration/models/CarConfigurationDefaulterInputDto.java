package ru.lebedev.dealership.application.contracts.carconfiguration.models;

import java.util.Map;

public record CarConfigurationDefaulterInputDto(
        long carVersionId,
        Map<String, Long> requiredDetails
) {
}
