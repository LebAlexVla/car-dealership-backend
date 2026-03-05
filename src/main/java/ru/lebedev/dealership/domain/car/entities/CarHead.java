package ru.lebedev.dealership.domain.car.entities;

import ru.lebedev.dealership.domain.car.enums.BodyType;
import ru.lebedev.dealership.domain.car.valueobjects.CarHeadId;
import ru.lebedev.dealership.domain.car.valueobjects.CarModel;
import ru.lebedev.dealership.domain.shared.valueobjects.Brand;

public record CarHead(
        CarHeadId id,
        Brand brand,
        CarModel model,
        BodyType bodyType
) {
}