package ru.lebedev.dealership.controller.car.models;

import java.math.BigDecimal;
import java.util.List;

public record CarVersionOutputDto(
        Long id,
        String name,
        Long carHeadId,
        String fuelType,
        Long power,
        double capacity,
        String gearBoxType,
        String carDrive,
        List<String> colors,
        BigDecimal price
) {
}