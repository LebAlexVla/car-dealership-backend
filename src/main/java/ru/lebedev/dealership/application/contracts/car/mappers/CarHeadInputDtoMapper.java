package ru.lebedev.dealership.application.contracts.car.mappers;

import ru.lebedev.dealership.application.contracts.car.models.CarHeadInputDto;
import ru.lebedev.dealership.domain.car.entities.CarHead;
import ru.lebedev.dealership.domain.car.enums.BodyType;
import ru.lebedev.dealership.domain.car.vo.CarHeadId;
import ru.lebedev.dealership.domain.car.vo.CarModel;
import ru.lebedev.dealership.domain.shared.vo.Brand;

public class CarHeadInputDtoMapper {
    public static CarHead map(CarHeadId id, CarHeadInputDto dto) {
        return new CarHead(
                id,
                new Brand(dto.brand()),
                new CarModel(dto.model()),
                BodyType.valueOf(dto.bodyType().trim().toUpperCase())
        );
    }
}