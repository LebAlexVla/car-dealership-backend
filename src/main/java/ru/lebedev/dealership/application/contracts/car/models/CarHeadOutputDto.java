package ru.lebedev.dealership.application.contracts.car.models;

import java.math.BigDecimal;
import java.util.List;

public record CarHeadOutputDto(
        String carHeadId,
        String brand,
        String model,
        String bodyType
) {
}
