package ru.lebedev.dealership.application.contracts.car.mappers;

import ru.lebedev.dealership.application.filters.CarVersionFilter;
import ru.lebedev.dealership.application.contracts.car.models.CarVersionFilterDto;
import ru.lebedev.dealership.domain.car.enums.CarDrive;
import ru.lebedev.dealership.domain.car.enums.FuelType;
import ru.lebedev.dealership.domain.car.enums.GearboxType;
import ru.lebedev.dealership.domain.car.vo.CarColor;
import ru.lebedev.dealership.domain.car.vo.EngineCapacity;
import ru.lebedev.dealership.domain.car.vo.EnginePower;
import ru.lebedev.dealership.domain.shared.vo.Price;

public class CarVersionFilterDtoMapper {
    public static CarVersionFilter map(CarVersionFilterDto dto) {
        return new CarVersionFilter(
                new Price(dto.minPrice()),
                new Price(dto.maxPrice()),
                FuelType.valueOf(dto.fuelType().trim().toUpperCase()),
                new EnginePower(dto.enginePower()),
                new EngineCapacity(dto.engineCapacity()),
                GearboxType.valueOf(dto.gearboxType().trim().toUpperCase()),
                CarDrive.valueOf(dto.carDrive().trim().toUpperCase()),
                new CarColor(dto.color())
        );
    }
}
