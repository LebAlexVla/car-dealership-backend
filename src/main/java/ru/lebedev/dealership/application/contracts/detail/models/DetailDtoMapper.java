package ru.lebedev.dealership.application.contracts.detail.models;

import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;
import ru.lebedev.dealership.domain.detail.DetailType;
import ru.lebedev.dealership.domain.shared.valueobjects.Price;
import ru.lebedev.dealership.domain.user.UserId;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class DetailDtoMapper {
    public static UserId mapUserId(DetailInputDto dto) {
        return new UserId(UUID.fromString(dto.userId()));
    }

    public static DetailType mapDetailType(DetailInputDto dto) {
        return new DetailType(dto.detailType());
    }

    public static Price mapPrice(DetailInputDto dto) {
        return new Price(dto.price());
    }

    public static Set<CarVersionId> mapCompatibleCars(DetailInputDto dto) {
        var rawCompatibleCars = dto.compatibleCars();
        var compatibleCars = new HashSet<CarVersionId>();
        for (var rawCarId : rawCompatibleCars) {
            compatibleCars.add(new CarVersionId(UUID.fromString(rawCarId)));
        }

        return compatibleCars;
    }
}
