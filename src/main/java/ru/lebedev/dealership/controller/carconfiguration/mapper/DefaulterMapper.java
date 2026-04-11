package ru.lebedev.dealership.controller.carconfiguration.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lebedev.dealership.controller.carconfiguration.dto.CarConfigurationDefaulterOutputDto;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationDefaulter;
import ru.lebedev.dealership.domain.detail.Detail;

@Mapper(componentModel = "spring")
public interface DefaulterMapper {
    @Mapping(target = "carVersionId", source = "carVersion.id")
    @Mapping(target = "requiredDetailsIds", source = "requiredDetails")
    CarConfigurationDefaulterOutputDto toDto(CarConfigurationDefaulter defaulter);

    default Long map(Detail detail) {
        return detail != null ? detail.getId() : null;
    }
}