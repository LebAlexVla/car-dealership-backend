package ru.lebedev.dealership.controller.order.configuredcar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lebedev.dealership.controller.order.configuredcar.dto.ConfiguredCarOrderOutputDto;
import ru.lebedev.dealership.domain.order.configuredcar.ConfiguredCarOrder;

@Mapper(componentModel = "spring")
public interface ConfiguredCarOrderMapper {
    @Mapping(target = "configurationId", source = "configuration.id")
    ConfiguredCarOrderOutputDto toDto(ConfiguredCarOrder order);
}