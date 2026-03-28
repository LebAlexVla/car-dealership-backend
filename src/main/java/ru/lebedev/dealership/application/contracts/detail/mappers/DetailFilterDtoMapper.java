package ru.lebedev.dealership.application.contracts.detail.mappers;

import ru.lebedev.dealership.application.filters.DetailFilter;
import ru.lebedev.dealership.application.contracts.detail.models.DetailFilterDto;
import ru.lebedev.dealership.domain.detail.DetailType;
import ru.lebedev.dealership.domain.shared.vo.Price;

public class DetailFilterDtoMapper {
    public static DetailFilter map(DetailFilterDto dto) {
        return new DetailFilter(
                new DetailType(dto.detailType()),
                new Price(dto.price()),
                dto.compatibleCars()
        );
    }
}
