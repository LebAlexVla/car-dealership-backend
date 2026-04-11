package ru.lebedev.dealership.controller.car.dto;

import java.math.BigDecimal;
import java.util.List;

public record CarVersionOutputDto(
        Long id,
        String name,
        Long carHeadId,
        EngineDto engineDto,
        String gearboxType,
        String carDrive,
        List<String> colors,
        BigDecimal price,
        boolean testDriveAvailable
) {
}