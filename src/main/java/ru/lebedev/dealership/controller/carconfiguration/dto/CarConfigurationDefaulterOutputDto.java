package ru.lebedev.dealership.controller.carconfiguration.dto;

import java.util.Set;

public record CarConfigurationDefaulterOutputDto(
        Long id,
        Long carVersionId,
        Set<Long> requiredDetailsIds
){
}