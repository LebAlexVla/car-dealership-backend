package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebedev.dealership.domain.testdrive.TestDriveCar;

@Repository
public interface TestDriveCarsRepository extends JpaRepository<TestDriveCar, Long> {
}
