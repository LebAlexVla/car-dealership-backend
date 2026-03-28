package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.lebedev.dealership.domain.car.entities.CarVersion;

@Repository
public interface CarVersionRepository extends JpaRepository<CarVersion, Long>, JpaSpecificationExecutor<CarVersion> {
}