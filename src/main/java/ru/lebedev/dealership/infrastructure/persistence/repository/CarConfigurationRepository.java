package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebedev.dealership.domain.carconfiguration.CarConfiguration;

@Repository
public interface CarConfigurationRepository extends JpaRepository<CarConfiguration, Long> {
}