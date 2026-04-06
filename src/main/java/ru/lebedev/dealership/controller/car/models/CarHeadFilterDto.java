package ru.lebedev.dealership.controller.car.models;

import java.util.List;

public record CarHeadFilterDto(
        List<String> brands,
        List<String> carModels,
        List<String> bodyTypes
) {
}