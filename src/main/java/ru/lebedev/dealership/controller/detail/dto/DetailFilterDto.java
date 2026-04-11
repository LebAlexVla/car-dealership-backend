package ru.lebedev.dealership.controller.detail.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record DetailFilterDto(
        List<String> detailTypes,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Set<Long> compatibleCarsIds
) {
}