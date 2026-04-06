package ru.lebedev.dealership.controller.carconfiguration.dto;

import java.util.Set;

public record CarConfigurationDefaulterInputDto(
        Long carVersionId,
        Set<Long> requiredDetailsIds
) {
}