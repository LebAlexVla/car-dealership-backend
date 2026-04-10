package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebedev.dealership.domain.order.configuredcar.ConfiguredCarOrder;

import java.util.Optional;

@Repository
public interface ConfiguredCarOrderRepository extends JpaRepository<ConfiguredCarOrder, Long> {
    Optional<ConfiguredCarOrder> findByConfigurationClientId(Long clientId);
}