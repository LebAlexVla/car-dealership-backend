package ru.lebedev.dealership.controller.car.dto;

import java.math.BigDecimal;
import java.util.List;

public record CarVersionFilterDto(
        BigDecimal minPrice,
        BigDecimal maxPrice,
        List<String> fuelTypes,
        Long minEnginePower,
        Long maxEnginePower,
        double minEngineCapacity,
        double maxEngineCapacity,
        List<String> gearboxTypes,
        List<String> carDrives,
        List<String> colors
) {
}