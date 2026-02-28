package ru.lebedev.dealership.application.contracts.car.models;

import ru.lebedev.dealership.domain.car.enums.BodyType;
import ru.lebedev.dealership.domain.car.valueobjects.CarModel;
import ru.lebedev.dealership.domain.shared.valueobjects.Brand;
import ru.lebedev.dealership.domain.user.UserId;

import java.util.UUID;

public class CarHeadDtoMapper {
    public static UserId mapUserId(CarHeadInputDto dto) {
        return new UserId(UUID.fromString(dto.userId()));
    }

    public static Brand mapBrand(CarHeadInputDto dto) {
        return new Brand(dto.brand());
    }

    public static CarModel mapModel(CarHeadInputDto dto) {
        return new CarModel(dto.model());
    }

    public static BodyType mapBodyType(CarHeadInputDto dto) {
        return BodyType.valueOf(dto.bodyType().trim().toUpperCase());
    }
}