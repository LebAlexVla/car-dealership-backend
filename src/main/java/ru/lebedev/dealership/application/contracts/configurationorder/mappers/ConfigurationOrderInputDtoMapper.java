package ru.lebedev.dealership.application.contracts.configurationorder.mappers;

import ru.lebedev.dealership.application.contracts.configurationorder.models.ConfigurationOrderInputDto;
import ru.lebedev.dealership.domain.order.configuration.ConfigurationOrder;

public class ConfigurationOrderInputDtoMapper {
    public static ConfigurationOrder map(ConfigurationOrderInputDto inputDto) {
        return new ConfigurationOrder(
                0,
                inputDto.clientId(),
                inputDto.carVersionId(),
                inputDto.configurationId()
        );
    }
}