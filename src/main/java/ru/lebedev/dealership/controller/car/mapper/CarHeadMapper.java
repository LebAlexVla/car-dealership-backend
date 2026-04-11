package ru.lebedev.dealership.controller.car.mapper;

import org.mapstruct.Mapper;
import ru.lebedev.dealership.application.filters.CarHeadFilter;
import ru.lebedev.dealership.controller.car.dto.CarHeadFilterDto;
import ru.lebedev.dealership.controller.car.dto.CarHeadInputDto;
import ru.lebedev.dealership.controller.car.dto.CarHeadOutputDto;
import ru.lebedev.dealership.domain.car.entities.CarHead;

@Mapper(componentModel = "spring")
public interface CarHeadMapper {
    CarHead toEntity(CarHeadInputDto dto);

    CarHeadOutputDto toDto(CarHead carHead);

    CarHeadFilter toFilter(CarHeadFilterDto dto);
}