package ru.lebedev.dealership.controller.carconfiguration.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lebedev.dealership.controller.carconfiguration.dto.CarConfigurationCustomizerOutputDto;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationCustomizer;
import ru.lebedev.dealership.domain.detail.Detail;

@Mapper(componentModel = "spring")
public interface CustomizerMapper {
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "carVersionId", source = "carVersion.id")
    @Mapping(target = "requiredDetailsIds", source = "requiredDetails")
    @Mapping(target = "optionalDetailsIds", source = "optionalDetails")
    CarConfigurationCustomizerOutputDto toDto(CarConfigurationCustomizer customizer);

    default Long map(Detail detail) {
        return detail.getId();
    }
}