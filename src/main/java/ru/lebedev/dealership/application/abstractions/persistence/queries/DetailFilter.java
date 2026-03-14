package ru.lebedev.dealership.application.abstractions.persistence.queries;

import ru.lebedev.dealership.domain.detail.DetailType;
import ru.lebedev.dealership.domain.shared.vo.Price;

import java.util.Set;

public record DetailFilter(
        DetailType detailType,
        Price price,
        Set<Long> compatibleCars
) {
}