package ru.lebedev.dealership.presentation.car.models;

import java.util.List;

public record CarHeadFilterDto(
        List<String> brand,
        List<String> carModel,
        List<String> bodyType
) {
}