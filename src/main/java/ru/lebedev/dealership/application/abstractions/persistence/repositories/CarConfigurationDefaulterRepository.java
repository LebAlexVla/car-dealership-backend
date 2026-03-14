package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationDefaulter;

public interface CarConfigurationDefaulterRepository {
    CarConfigurationDefaulter save(CarConfigurationDefaulter carConfigurationDefaulter);

    void delete(long carVersionId);

    CarConfigurationDefaulter findById(long carConfigurationDefaulter);
}