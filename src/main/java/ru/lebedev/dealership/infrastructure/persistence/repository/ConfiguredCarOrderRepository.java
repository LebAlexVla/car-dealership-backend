package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lebedev.dealership.domain.order.configuredcar.ConfiguredCarOrder;

public interface ConfiguredCarOrderRepository extends JpaRepository<ConfiguredCarOrder, Long> {
}