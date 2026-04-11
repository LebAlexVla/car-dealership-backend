package ru.lebedev.dealership.controller.carconfiguration.dto;

import java.util.Set;

public record CarConfigurationCustomizerOutputDto(
        Long id,
        Long clientId,
        Long carVersionId,
        Set<Long> requiredDetailsIds,
        Set<Long> optionalDetailsIds
) {
}