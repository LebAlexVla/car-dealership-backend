package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.domain.order.configuredcar.ConfiguredCarOrder;

import java.util.List;

public interface ConfiguredCarOrderRepository {
    ConfiguredCarOrder save(ConfiguredCarOrder order);

    void delete(Long orderId);

    ConfiguredCarOrder findById(Long orderId);

    List<ConfiguredCarOrder> findAll();
}