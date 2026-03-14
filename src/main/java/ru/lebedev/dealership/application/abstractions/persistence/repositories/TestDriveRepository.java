package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.domain.testdrive.TestDrive;

import java.util.List;

public interface TestDriveRepository {
    TestDrive save(TestDrive testDrive);

    void delete(Long testDriveId);

    TestDrive findById(Long testDriveId);

    List<TestDrive> findAll();
}
