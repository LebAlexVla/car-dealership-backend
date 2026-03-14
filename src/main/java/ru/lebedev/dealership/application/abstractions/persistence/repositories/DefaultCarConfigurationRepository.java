package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationBuilderFactory;

public interface DefaultCarConfigurationRepository {
    CarConfigurationBuilderFactory save(CarConfigurationBuilderFactory carConfigurationBuilderFactory);

    void delete(long carVersionId);

    CarConfigurationBuilderFactory findById(CarConfigurationBuilderFactory carConfigurationBuilderFactory);
}