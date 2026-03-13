package ru.lebedev.dealership.application.contracts.car.mappers;

import ru.lebedev.dealership.application.contracts.car.models.CarVersionOutputDto;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.car.vo.CarColor;

import java.util.List;

public class CarVersionOutputDtoMapper {
    public static CarVersionOutputDto map(CarVersion carVersion) {
        return new CarVersionOutputDto(
                carVersion.id().value().toString(),
                carVersion.name(),
                carVersion.headId().value().toString(),
                carVersion.engine().fuelType().toString(),
                carVersion.engine().power().horsepower(),
                carVersion.engine().capacity().liters(),
                carVersion.gearboxType().toString(),
                carVersion.carDrive().toString(),
                mapColors(carVersion.colors()),
                carVersion.price().rubles()
        );
    }

    private static List<String> mapColors(List<CarColor> colors) {
        return colors
                .stream()
                .map(CarColor::color)
                .toList();
    }
}
