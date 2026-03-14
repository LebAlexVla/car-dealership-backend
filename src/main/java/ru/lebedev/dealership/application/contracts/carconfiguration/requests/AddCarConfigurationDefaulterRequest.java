package ru.lebedev.dealership.application.contracts.carconfiguration.requests;

import ru.lebedev.dealership.application.contracts.carconfiguration.models.CarConfigurationDefaulterInputDto;

public record AddCarConfigurationDefaulterRequest(CarConfigurationDefaulterInputDto inputDto) {
}