package ru.lebedev.dealership.application.contracts.carconfiguration.requests;

public record AddCustomDetailRequest(long carConfigurationCustomizerId, long detailId) {
}