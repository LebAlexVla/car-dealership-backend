package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.domain.configuration.ConfigurationBuilder;

public interface ConfigurationCustomRepository {
    ConfigurationBuilder save(ConfigurationBuilder configurationBuilder);

    ConfigurationBuilder findById(long clientId);

    void delete(long clientId);
}