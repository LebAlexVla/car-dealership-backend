package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.lebedev.dealership.domain.car.entities.CarHead;

@Repository
public interface CarHeadRepository extends JpaRepository<CarHead, Long>, JpaSpecificationExecutor<CarHead> {
}