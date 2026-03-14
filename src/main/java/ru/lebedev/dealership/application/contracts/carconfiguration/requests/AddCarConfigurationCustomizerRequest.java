package ru.lebedev.dealership.application.contracts.carconfiguration.requests;

public record AddCarConfigurationCustomizerRequest(long clientId, long carConfigurationDefaulterId) {
}