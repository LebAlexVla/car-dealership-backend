package ru.lebedev.dealership.application.contracts.detail.mappers;

import ru.lebedev.dealership.application.contracts.detail.models.DetailInputDto;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.detail.DetailType;
import ru.lebedev.dealership.domain.shared.vo.Price;

public class DetailInputDtoMapper {
    public static Detail map(DetailInputDto dto) {
        return new Detail(
                0L,
                dto.detailName(),
                new DetailType(dto.detailType()),
                new Price(dto.price()),
                dto.compatibleCars()
        );
    }
}