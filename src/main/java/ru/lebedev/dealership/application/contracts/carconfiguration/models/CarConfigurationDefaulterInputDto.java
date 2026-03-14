package ru.lebedev.dealership.application.contracts.carconfiguration.models;

import java.util.Map;

public record CarConfigurationDefaulterInputDto(
        Long carVersionId,
        Map<String, Long> requiredDetails
) {
}