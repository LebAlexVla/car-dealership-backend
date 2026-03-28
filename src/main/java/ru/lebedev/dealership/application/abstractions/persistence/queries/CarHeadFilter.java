package ru.lebedev.dealership.application.abstractions.persistence.queries;

import ru.lebedev.dealership.domain.car.enums.BodyType;

import java.util.List;

public record CarHeadFilter(
        List<String> brands,
        List<String> carModels,
        List<BodyType> bodyTypes
) {
}