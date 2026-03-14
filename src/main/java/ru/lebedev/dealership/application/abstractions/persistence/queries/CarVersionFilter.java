package ru.lebedev.dealership.application.abstractions.persistence.queries;

import ru.lebedev.dealership.domain.car.enums.CarDrive;
import ru.lebedev.dealership.domain.car.enums.FuelType;
import ru.lebedev.dealership.domain.car.enums.GearboxType;
import ru.lebedev.dealership.domain.car.vo.CarColor;
import ru.lebedev.dealership.domain.car.vo.EngineCapacity;
import ru.lebedev.dealership.domain.car.vo.EnginePower;
import ru.lebedev.dealership.domain.shared.vo.Price;

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
