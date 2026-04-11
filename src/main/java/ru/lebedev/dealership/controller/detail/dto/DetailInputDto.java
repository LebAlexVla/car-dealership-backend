package ru.lebedev.dealership.controller.detail.dto;

import java.math.BigDecimal;
import java.util.Set;

public record DetailInputDto(
        String name,
        String type,
        BigDecimal price,
        Set<Long> compatibleCars
) {
}