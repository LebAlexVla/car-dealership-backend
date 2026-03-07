package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.domain.order.configuration.ConfigurationOrder;

import java.util.List;

public interface ConfigurationOrderRepository {
    ConfigurationOrder save(ConfigurationOrder order);

    List<ConfigurationOrder> findAll();
}