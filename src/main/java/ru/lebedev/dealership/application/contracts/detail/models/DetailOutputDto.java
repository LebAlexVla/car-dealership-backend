package ru.lebedev.dealership.application.contracts.detail.models;

import java.math.BigDecimal;
import java.util.Set;

public record DetailOutputDto(
        Long detailId,
        String detailName,
        String detailType,
        BigDecimal price,
        Set<Long> compatibleCars
) {
}
