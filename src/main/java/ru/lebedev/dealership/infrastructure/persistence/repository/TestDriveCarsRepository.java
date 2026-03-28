package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lebedev.dealership.domain.testdrive.TestDriveCar;

public interface TestDriveCarsRepository extends JpaRepository<TestDriveCar, Long> {
}
