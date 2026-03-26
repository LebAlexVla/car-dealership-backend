package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.lebedev.dealership.domain.car.entities.CarHead;

public interface CarHeadRepository extends CrudRepository<CarHead, Long>, JpaSpecificationExecutor<CarHead> {
}
