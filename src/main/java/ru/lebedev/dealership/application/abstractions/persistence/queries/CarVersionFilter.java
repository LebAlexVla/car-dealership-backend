package ru.lebedev.dealership.application.abstractions.persistence.queries;

import ru.lebedev.dealership.domain.car.enums.CarDrive;
import ru.lebedev.dealership.domain.car.enums.FuelType;
import ru.lebedev.dealership.domain.car.enums.GearboxType;
import ru.lebedev.dealership.domain.car.valueobjects.CarColor;
import ru.lebedev.dealership.domain.car.valueobjects.EngineCapacity;
import ru.lebedev.dealership.domain.car.valueobjects.EnginePower;
import ru.lebedev.dealership.domain.shared.valueobjects.Price;

public record CarVersionFilter(
        Price minPrice,
        Price maxPrice,
        FuelType fuelType,
        EnginePower enginePower,
        EngineCapacity engineCapacity,
        GearboxType gearboxType,
        CarDrive carDrive,
        CarColor color
) {
}
