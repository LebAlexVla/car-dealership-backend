package ru.lebedev.dealership.domain.car.vo;

import ru.lebedev.dealership.domain.car.enums.FuelType;

public record Engine(
        FuelType fuelType,
        EnginePower power,
        EngineCapacity capacity
) {
}