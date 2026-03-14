package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.domain.order.configuredcar.ConfiguredCarOrder;

import java.util.List;

public interface ConfiguredCarOrderRepository {
    ConfiguredCarOrder save(ConfiguredCarOrder order);

    void delete(long orderId);

    ConfiguredCarOrder findById(long orderId);

    List<ConfiguredCarOrder> findAll();
}