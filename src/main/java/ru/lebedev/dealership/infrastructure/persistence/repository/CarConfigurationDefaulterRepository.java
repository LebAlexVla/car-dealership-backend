package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationDefaulter;

import java.util.Optional;

@Repository
public interface CarConfigurationDefaulterRepository extends JpaRepository<CarConfigurationDefaulter, Long> {
    Optional<CarConfigurationDefaulter> findByCarVersionId(Long carVersionId);

    void deleteByCarVersionId(Long carVersionId);
}