package ru.lebedev.dealership.application.abstractions.persistence.queries;

import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;
import ru.lebedev.dealership.domain.detail.DetailType;
import ru.lebedev.dealership.domain.shared.valueobjects.Price;

import java.util.Set;

public record DetailFilter(
        DetailType detailType,
        Price price,
        Set<CarVersionId> compatibleCars
) {
}