package ru.lebedev.dealership.application.contracts.car.mappers;

import ru.lebedev.dealership.application.abstractions.persistence.queries.CarHeadFilter;
import ru.lebedev.dealership.application.contracts.car.models.CarHeadFilterDto;
import ru.lebedev.dealership.domain.car.enums.BodyType;
import ru.lebedev.dealership.domain.car.vo.CarModel;
import ru.lebedev.dealership.domain.shared.vo.Brand;

public class CarHeadFilterDtoMapper {
    public static CarHeadFilter map(CarHeadFilterDto dto) {
        return new CarHeadFilter(
                new Brand(dto.brand()),
                new CarModel(dto.carModel()),
                BodyType.valueOf(dto.bodyType().trim().toUpperCase())
        );
    }
}
