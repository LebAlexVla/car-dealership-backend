package ru.lebedev.dealership.domain.car.valueobjects;

import ru.lebedev.dealership.domain.car.enums.FuelType;

public record Engine(
        FuelType fuelType,
        EnginePower power,
        EngineCapacity capacity
) {
}