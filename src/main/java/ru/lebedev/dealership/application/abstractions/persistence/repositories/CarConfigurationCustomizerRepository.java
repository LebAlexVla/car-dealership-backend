package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationCustomizer;

public interface CarConfigurationCustomizerRepository {
    CarConfigurationCustomizer save(CarConfigurationCustomizer carConfigurationCustomizer);

    CarConfigurationCustomizer findById(Long carConfigurationCustomizerId);

    void delete(Long clientId);
}