package ru.lebedev.dealership.controller.carconfiguration.models;

import java.util.Set;

public record CarConfigurationDefaulterOutputDto(
        Long id,
        Long carVersionId,
        Set<Long> requiredDetailsIds
){
}