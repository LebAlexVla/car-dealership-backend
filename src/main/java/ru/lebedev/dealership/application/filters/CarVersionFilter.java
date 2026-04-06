package ru.lebedev.dealership.application.filters;

import ru.lebedev.dealership.domain.car.enums.CarDrive;
import ru.lebedev.dealership.domain.car.enums.FuelType;
import ru.lebedev.dealership.domain.car.enums.GearboxType;
import ru.lebedev.dealership.domain.car.vo.EngineCapacity;
import ru.lebedev.dealership.domain.car.vo.EnginePower;
import ru.lebedev.dealership.domain.car.vo.Price;

import java.util.List;

public record CarVersionFilter(
        Price minPrice,
        Price maxPrice,
        List<FuelType> fuelTypes,
        EnginePower minEnginePower,
        EnginePower maxEnginePower,
        EngineCapacity minEngineCapacity,
        EngineCapacity maxEngineCapacity,
        List<GearboxType> gearboxTypes,
        List<CarDrive> carDrives,
        List<String> colors
) {
}
