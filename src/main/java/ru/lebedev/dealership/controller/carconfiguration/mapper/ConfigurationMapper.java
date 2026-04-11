package ru.lebedev.dealership.controller.carconfiguration.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lebedev.dealership.controller.carconfiguration.dto.CarConfigurationOutputDto;
import ru.lebedev.dealership.domain.carconfiguration.CarConfiguration;
import ru.lebedev.dealership.domain.detail.Detail;

@Mapper(componentModel = "spring")
public interface ConfigurationMapper {
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "carVersionId", source = "carVersion.id")
    @Mapping(target = "detailsIds", source = "details")
    CarConfigurationOutputDto toDto(CarConfiguration configuration);

    default Long map(Detail detail) {
        return detail.getId();
    }
}