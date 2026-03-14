package ru.lebedev.dealership.domain.car.entities;

import ru.lebedev.dealership.domain.car.enums.BodyType;
import ru.lebedev.dealership.domain.car.vo.CarModel;
import ru.lebedev.dealership.domain.shared.vo.Brand;

public record CarHead(
        Long carHeadId,
        Brand brand,
        CarModel model,
        BodyType bodyType
) {
}