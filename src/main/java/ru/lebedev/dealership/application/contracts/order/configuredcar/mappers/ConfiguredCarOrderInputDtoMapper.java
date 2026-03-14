package ru.lebedev.dealership.application.contracts.order.configuredcar.mappers;

import ru.lebedev.dealership.application.contracts.order.configuredcar.models.ConfiguredCarOrderInputDto;
import ru.lebedev.dealership.domain.order.configuredcar.ConfiguredCarOrder;

public class ConfiguredCarOrderInputDtoMapper {
    public static ConfiguredCarOrder map(ConfiguredCarOrderInputDto inputDto) {
        return new ConfiguredCarOrder(
                0L,
                inputDto.clientId(),
                inputDto.carVersionId(),
                inputDto.configurationId()
        );
    }
}