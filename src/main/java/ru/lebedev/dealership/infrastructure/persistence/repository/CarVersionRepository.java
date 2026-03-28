package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.lebedev.dealership.domain.car.entities.CarVersion;

public interface CarVersionRepository extends JpaRepository<CarVersion, Long>, JpaSpecificationExecutor<CarVersion> {
}