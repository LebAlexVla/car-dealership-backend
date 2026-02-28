package ru.lebedev.dealership.application.contracts.car.mappers;

import ru.lebedev.dealership.application.contracts.car.models.CarVersionInputDto;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.car.enums.CarDrive;
import ru.lebedev.dealership.domain.car.enums.FuelType;
import ru.lebedev.dealership.domain.car.enums.GearboxType;
import ru.lebedev.dealership.domain.car.valueobjects.*;
import ru.lebedev.dealership.domain.shared.valueobjects.Price;

import java.util.List;
import java.util.UUID;

public class CarVersionInputDtoMapper {
    public static CarVersion map(CarVersionId id, CarVersionInputDto dto) {
        return new CarVersion(
                id,
                dto.carVersionName(),
                new CarHeadId(UUID.fromString(dto.carHeadId())),
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
