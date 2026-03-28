package ru.lebedev.dealership.presentation.carconfiguration;

import java.util.Map;

public record CarConfigurationDefaulterInputDto(
        Long carVersionId,
        Map<String, Long> requiredDetails
) {
}