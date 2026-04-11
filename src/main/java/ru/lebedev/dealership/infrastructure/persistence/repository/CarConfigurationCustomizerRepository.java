package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationCustomizer;

@Repository
public interface CarConfigurationCustomizerRepository extends JpaRepository<CarConfigurationCustomizer, Long> {
}