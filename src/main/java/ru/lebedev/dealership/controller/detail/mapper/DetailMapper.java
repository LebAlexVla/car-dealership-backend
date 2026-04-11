package ru.lebedev.dealership.controller.detail.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lebedev.dealership.application.filters.DetailFilter;
import ru.lebedev.dealership.controller.car.mapper.PriceMapper;
import ru.lebedev.dealership.controller.detail.dto.DetailFilterDto;
import ru.lebedev.dealership.controller.detail.dto.DetailInputDto;
import ru.lebedev.dealership.controller.detail.dto.DetailOutputDto;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.detail.Detail;

@Mapper(componentModel = "spring", uses = PriceMapper.class)
public interface DetailMapper {
    @Mapping(target = "compatibleCars", ignore = true)
    Detail toEntity(DetailInputDto dto);

    DetailOutputDto toDto(Detail detail);

    DetailFilter toFilter(DetailFilterDto filterDto);

    default Long map(CarVersion carVersion) {
        return carVersion != null ? carVersion.getId() : null;
    }
}