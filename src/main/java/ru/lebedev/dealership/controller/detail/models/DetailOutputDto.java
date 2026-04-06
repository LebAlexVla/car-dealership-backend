package ru.lebedev.dealership.controller.detail.models;

import java.math.BigDecimal;
import java.util.Set;

public record DetailOutputDto(
        Long id,
        String name,
        String type,
        BigDecimal price,
        Set<Long> compatibleCars
) {
}