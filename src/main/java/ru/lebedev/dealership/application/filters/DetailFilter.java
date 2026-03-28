package ru.lebedev.dealership.application.filters;

import ru.lebedev.dealership.domain.shared.vo.Price;

import java.util.List;
import java.util.Set;

public record DetailFilter(
        List<String> detailType,
        Price minPrice,
        Price maxPrice,
        Set<Long> compatibleCarsIds
) {
}