package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.domain.configuration.ConfigurationBuilderFactory;

public interface ConfigurationDefaultRepository {
    ConfigurationBuilderFactory save(ConfigurationBuilderFactory configurationBuilderFactory);

    void delete(long carVersionId);

    ConfigurationBuilderFactory findById(ConfigurationBuilderFactory configurationBuilderFactory);
}