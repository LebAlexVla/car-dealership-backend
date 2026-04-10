package ru.lebedev.dealership.domain.car.vo;

import jakarta.persistence.*;
import ru.lebedev.dealership.domain.car.enums.FuelType;

@Embeddable
public record Engine(
        @Enumerated(EnumType.STRING)
        @Column(name = "engine_fuel_type")
        FuelType fuelType,

        @Embedded
        EnginePower power,

        @Embedded
        EngineCapacity capacity
) {
}