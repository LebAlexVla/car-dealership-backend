package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationDefaulter;

@Repository
public interface CarConfigurationDefaulterRepository extends JpaRepository<CarConfigurationDefaulter, Long> {
}