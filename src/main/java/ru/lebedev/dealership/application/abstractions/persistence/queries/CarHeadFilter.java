package ru.lebedev.dealership.application.abstractions.persistence.queries;

import ru.lebedev.dealership.domain.car.enums.BodyType;
import ru.lebedev.dealership.domain.car.valueobjects.CarModel;
import ru.lebedev.dealership.domain.shared.valueobjects.Brand;

public record CarHeadFilter(
        Brand brand,
        CarModel carModel,
        BodyType bodyType
) {
}