package ru.lebedev.dealership.application.abstractions.persistence.queries;

import ru.lebedev.dealership.domain.car.enums.BodyType;
import ru.lebedev.dealership.domain.car.vo.CarModel;
import ru.lebedev.dealership.domain.shared.vo.Brand;

public record CarHeadFilter(
        Brand brand,
        CarModel carModel,
        BodyType bodyType
) {
}