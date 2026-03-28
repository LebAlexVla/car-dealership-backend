package ru.lebedev.dealership.presentation.car.models;

import java.math.BigDecimal;

public record CarVersionFilterDto(
        BigDecimal minPrice,
        BigDecimal maxPrice,
        String fuelType,
        Long enginePower,
        double engineCapacity,
        String gearboxType,
        String carDrive,
        String color
) {
}