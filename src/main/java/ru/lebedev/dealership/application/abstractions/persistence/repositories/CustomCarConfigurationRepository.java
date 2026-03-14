package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationBuilder;

public interface CustomCarConfigurationRepository {
    CarConfigurationBuilder save(CarConfigurationBuilder carConfigurationBuilder);

    CarConfigurationBuilder findById(long clientId);

    void delete(long clientId);
}