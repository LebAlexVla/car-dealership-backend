package ru.lebedev.dealership.application.contracts.car.filters;

import ru.lebedev.dealership.domain.car.enums.BodyType;
import ru.lebedev.dealership.domain.car.valueobjects.CarModel;
import ru.lebedev.dealership.domain.shared.valueobjects.Brand;

public record CarHeadFilter(
        Brand brand,
        CarModel carModel,
        BodyType bodyType
) {}
