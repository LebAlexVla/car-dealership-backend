package ru.lebedev.dealership.application.contracts.car.models;

import java.math.BigDecimal;
import java.util.List;

public record CarVersionOutputDto(
        String carVersionId,
        String carVersionName,
        String carHeadId,
        String fuelType,
        Long power,
        double capacity,
        String gearBoxType,
        String carDrive,
        List<String> colors,
        BigDecimal price
) {
}