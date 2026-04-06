package ru.lebedev.dealership.controller.carconfiguration.models;

import java.util.Set;

public record CarConfigurationDefaulterInputDto(
        Long carVersionId,
        Set<Long> requiredDetailsIds
) {
}