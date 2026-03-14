package ru.lebedev.dealership.application.contracts.carconfiguration.requests;

public record AddCarConfigurationCustomizerRequest(Long clientId, Long carConfigurationDefaulterId) {
}