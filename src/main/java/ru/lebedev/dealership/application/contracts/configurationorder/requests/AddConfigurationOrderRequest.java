package ru.lebedev.dealership.application.contracts.configurationorder.requests;

import ru.lebedev.dealership.application.contracts.configurationorder.models.ConfigurationOrderInputDto;

public record AddConfigurationOrderRequest(ConfigurationOrderInputDto inputDto) {
}
