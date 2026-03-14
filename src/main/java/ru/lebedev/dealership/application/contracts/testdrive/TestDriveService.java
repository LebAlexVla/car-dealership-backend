package ru.lebedev.dealership.application.contracts.testdrive;

import ru.lebedev.dealership.application.contracts.testdrive.models.TestDriveOutputDto;
import ru.lebedev.dealership.application.contracts.testdrive.requests.*;

import java.util.List;

public interface TestDriveService {
    long addTestDrive(AddTestDriveRequest request);

    void deleteTestDrive(DeleteTestDriveRequest request);

    List<TestDriveOutputDto> showTestDrives(ShowTestDrivesRequest request);

    void addCars(AddCarsRequest request);

    void removeCars(RemoveCarsRequest request);
}