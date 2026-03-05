package ru.lebedev.dealership.application.contracts.detail.mappers;

import ru.lebedev.dealership.application.contracts.detail.models.DetailOutputDto;
import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;
import ru.lebedev.dealership.domain.detail.Detail;

import java.util.Set;
import java.util.stream.Collectors;

public class DetailOutputDtoMapper {
    public static DetailOutputDto map(Detail detail) {
        return new DetailOutputDto(
                detail.id().value().toString(),
                detail.name(),
                detail.type().toString(),
                detail.price().rubles(),
                mapCompatibleCars(detail.compatibleCars())
        );
    }

    private static Set<String> mapCompatibleCars(Set<CarVersionId> compatibleCars) {
        return compatibleCars
                .stream()
                .map(carVersionId -> carVersionId.value().toString())
                .collect(Collectors.toSet());
    }
}
