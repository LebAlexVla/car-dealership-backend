package ru.lebedev.dealership.application.contracts.carconfiguration.requests;

public record AddCustomDetailRequest(Long carConfigurationCustomizerId, Long detailId) {
}