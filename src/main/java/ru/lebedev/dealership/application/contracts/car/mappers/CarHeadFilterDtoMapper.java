package ru.lebedev.dealership.application.contracts.car.mappers;

import ru.lebedev.dealership.application.abstractions.persistence.queries.CarHeadFilter;
import ru.lebedev.dealership.application.contracts.car.models.CarHeadFilterDto;
import ru.lebedev.dealership.domain.car.enums.BodyType;

public class CarHeadFilterDtoMapper {
    public static CarHeadFilter map(CarHeadFilterDto dto) {
        return new CarHeadFilter(
                new dto.brand(),
                new dto.carModel(),
                BodyType.valueOf(dto.bodyType().trim().toUpperCase())
        );
    }
}
