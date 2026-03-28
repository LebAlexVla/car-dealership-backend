package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lebedev.dealership.domain.carconfiguration.CarConfiguration;

public interface CarConfigurationRepository extends JpaRepository<CarConfiguration, Long> {
}