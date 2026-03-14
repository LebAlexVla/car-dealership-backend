package ru.lebedev.dealership.application.contracts.detail.mappers;

import ru.lebedev.dealership.application.abstractions.persistence.queries.DetailFilter;
import ru.lebedev.dealership.application.contracts.detail.models.DetailFilterDto;
import ru.lebedev.dealership.domain.detail.DetailType;
import ru.lebedev.dealership.domain.shared.vo.Price;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class DetailFilterDtoMapper {
    public static DetailFilter map(DetailFilterDto dto) {
        return new DetailFilter(
                new DetailType(dto.detailType()),
                new Price(dto.price()),
                dto.compatibleCars()
        );
    }
}
