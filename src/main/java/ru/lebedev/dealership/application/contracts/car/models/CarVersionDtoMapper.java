package ru.lebedev.dealership.application.contracts.car.models;

import ru.lebedev.dealership.domain.car.enums.CarDrive;
import ru.lebedev.dealership.domain.car.enums.FuelType;
import ru.lebedev.dealership.domain.car.enums.GearboxType;
import ru.lebedev.dealership.domain.car.valueobjects.*;
import ru.lebedev.dealership.domain.shared.valueobjects.Price;
import ru.lebedev.dealership.domain.user.UserId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarVersionDtoMapper {
    public static UserId mapUserId(CarVersionInputDto dto) {
        return new UserId(UUID.fromString(dto.userId()));
    }

    public static CarHeadId mapCarHeadId(CarVersionInputDto dto) {
        return new CarHeadId(UUID.fromString(dto.carHeadId()));
    }

    public static Engine mapEngine(CarVersionInputDto dto) {
        return new Engine(
                FuelType.valueOf(dto.fuelType().trim().toUpperCase()),
                new EnginePower(dto.power()),
                new EngineCapacity(dto.capacity())
        );
    }

    public static GearboxType mapGearboxType(CarVersionInputDto dto) {
        return GearboxType.valueOf(dto.gearBoxType().trim().toUpperCase());
    }

    public static CarDrive mapCarDrive(CarVersionInputDto dto) {
        return CarDrive.valueOf(dto.carDrive().trim().toUpperCase());
    }

    public static List<CarColor> mapColors(CarVersionInputDto dto) {
        var rawColors = dto.colors();
        ArrayList<CarColor> colors = new ArrayList<CarColor>();
        for (var rawColor : rawColors) {
            colors.add(new CarColor(rawColor));
        }

        return colors;
    }

    public static Price mapPrice(CarVersionInputDto dto) {
        return new Price(dto.price());
    }
}
