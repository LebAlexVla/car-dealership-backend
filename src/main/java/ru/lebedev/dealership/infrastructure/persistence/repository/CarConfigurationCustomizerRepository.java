package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationCustomizer;

public interface CarConfigurationCustomizerRepository extends JpaRepository<CarConfigurationCustomizer, Long> {
}