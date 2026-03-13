package ru.lebedev.dealership.application.contracts.car.models;

import java.math.BigDecimal;
import java.util.List;

public record CarVersionInputDto(
        String carVersionName,
        long carHeadId,
        String fuelType,
        long power,
        double capacity,
        String gearBoxType,
        String carDrive,
        List<String> colors,
        BigDecimal price
) {
}