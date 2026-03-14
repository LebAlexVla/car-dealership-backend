package ru.lebedev.dealership.application.contracts.testdrive;

import ru.lebedev.dealership.application.contracts.testdrive.models.TestDriveOutputDto;
import ru.lebedev.dealership.application.contracts.testdrive.requests.*;

import java.util.List;

public interface TestDriveService {
    long AddTestDrive(AddTestDriveRequest request);

    void DeleteTestDrive(DeleteTestDriveRequest request);

    List<TestDriveOutputDto> ShowTestDrives(ShowTestDrivesRequest request);

    void AddCars(AddCarsRequest request);

    void RemoveCars(RemoveCarsRequest request);
}