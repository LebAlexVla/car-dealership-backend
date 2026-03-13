package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.domain.testdrive.TestDrive;

import java.util.List;

public interface TestDriveRepository {
    TestDrive save(TestDrive testDrive);

    void delete(long testDriveId);

    TestDrive findById(long testDriveId);

    List<TestDrive> findAll();
}
