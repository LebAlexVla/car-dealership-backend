package ru.lebedev.dealership.application.filters;

import ru.lebedev.dealership.domain.car.enums.BodyType;

import java.util.List;

public record CarHeadFilter(
        List<String> brands,
        List<String> carModels,
        List<BodyType> bodyTypes
) {
}