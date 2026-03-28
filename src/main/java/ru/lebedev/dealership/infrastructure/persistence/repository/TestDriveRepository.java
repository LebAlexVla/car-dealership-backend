package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lebedev.dealership.domain.testdrive.TestDrive;

public interface TestDriveRepository extends JpaRepository<TestDrive, Long> {
}