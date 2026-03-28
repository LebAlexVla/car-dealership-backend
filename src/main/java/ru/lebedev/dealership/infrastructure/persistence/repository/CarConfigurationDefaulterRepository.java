package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationDefaulter;

public interface CarConfigurationDefaulterRepository extends JpaRepository<CarConfigurationDefaulter, Long> {
}