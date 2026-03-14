package ru.lebedev.dealership.application.contracts.order.configuredcar.requests;

import ru.lebedev.dealership.application.contracts.order.configuredcar.models.ConfiguredCarOrderInputDto;

public record AddConfigurationOrderRequest(ConfiguredCarOrderInputDto inputDto) {
}
