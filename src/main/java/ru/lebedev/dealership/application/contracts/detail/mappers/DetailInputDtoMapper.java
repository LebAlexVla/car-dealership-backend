package ru.lebedev.dealership.application.contracts.detail.mappers;

import ru.lebedev.dealership.application.contracts.detail.models.DetailInputDto;
import ru.lebedev.dealership.domain.car.vo.CarVersionId;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.detail.DetailId;
import ru.lebedev.dealership.domain.detail.DetailType;
import ru.lebedev.dealership.domain.shared.vo.Price;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class DetailInputDtoMapper {
    public static Detail map(DetailId detailId, DetailInputDto dto) {
        return new Detail(
                detailId,
                dto.detailName(),
                new DetailType(dto.detailType()),
                new Price(dto.price()),
                mapCompatibleCars(dto.compatibleCars())
        );
    }

    private static Set<CarVersionId> mapCompatibleCars(Set<String> rawCompatibleCars) {
        var compatibleCars = new HashSet<CarVersionId>();
        for (var rawCarId : rawCompatibleCars) {
            compatibleCars.add(new CarVersionId(UUID.fromString(rawCarId)));
        }

        return compatibleCars;
    }
}
