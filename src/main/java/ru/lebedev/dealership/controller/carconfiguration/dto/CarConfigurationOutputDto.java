package ru.lebedev.dealership.controller.carconfiguration.dto;

import java.util.Set;

public record CarConfigurationOutputDto(
        Long id,
        Long clientId,
        Long carVersionId,
        Set<Long> detailsIds
) {
}