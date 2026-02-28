package ru.lebedev.dealership.application.contracts.car.models;

import java.math.BigDecimal;

public record CarVersionFilterDto(
        BigDecimal minPrice,
        BigDecimal maxPrice,
        String fuelType,
        long enginePower,
        double engineCapacity,
        String gearboxType,
        String carDrive,
        String color
) {
}