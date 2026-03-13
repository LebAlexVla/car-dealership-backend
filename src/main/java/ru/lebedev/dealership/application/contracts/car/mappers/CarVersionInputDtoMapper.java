package ru.lebedev.dealership.application.contracts.car.mappers;

import ru.lebedev.dealership.application.contracts.car.models.CarVersionInputDto;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.car.enums.CarDrive;
import ru.lebedev.dealership.domain.car.enums.FuelType;
import ru.lebedev.dealership.domain.car.enums.GearboxType;
import ru.lebedev.dealership.domain.car.vo.CarColor;
import ru.lebedev.dealership.domain.car.vo.Engine;
import ru.lebedev.dealership.domain.car.vo.EngineCapacity;
import ru.lebedev.dealership.domain.car.vo.EnginePower;
import ru.lebedev.dealership.domain.shared.vo.Price;

import java.util.List;

public class CarVersionInputDtoMapper {
    public static CarVersion map(CarVersionInputDto dto) {
        return new CarVersion(
                0,
                dto.carVersionName(),
                dto.carHeadId(),
                mapEngine(dto),
                GearboxType.valueOf(dto.gearBoxType().trim().toUpperCase()),
                CarDrive.valueOf(dto.carDrive().trim().toUpperCase()),
                mapColors(dto.colors()),
                new Price(dto.price())
        );
    }

    private static Engine mapEngine(CarVersionInputDto dto) {
        return new Engine(
                FuelType.valueOf(dto.fuelType().trim().toUpperCase()),
                new EnginePower(dto.power()),
                new EngineCapacity(dto.capacity())
        );
    }

    private static List<CarColor> mapColors(List<String> rawColors) {
        return rawColors
                .stream()
                .map(CarColor::new)
                .toList();
    }
}