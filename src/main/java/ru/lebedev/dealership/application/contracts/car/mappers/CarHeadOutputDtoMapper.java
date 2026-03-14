package ru.lebedev.dealership.application.contracts.car.mappers;

import ru.lebedev.dealership.application.contracts.car.models.CarHeadOutputDto;
import ru.lebedev.dealership.domain.car.entities.CarHead;

public class CarHeadOutputDtoMapper {
    public static CarHeadOutputDto map(CarHead carHead) {
        return new CarHeadOutputDto(
                carHead.carHeadId(),
                carHead.brand().name(),
                carHead.model().name(),
                carHead.bodyType().toString()
        );
    }
}
