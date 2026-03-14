package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.domain.carconfiguration.CarConfiguration;

public interface CarConfigurationRepository {
    CarConfiguration save(CarConfiguration carConfiguration);

    void delete(Long carConfigurationId);

    CarConfiguration findById(Long carConfigurationId);
}