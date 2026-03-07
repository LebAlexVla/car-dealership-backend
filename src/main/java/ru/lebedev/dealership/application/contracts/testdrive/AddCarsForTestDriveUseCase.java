package ru.lebedev.dealership.application.contracts.testdrive;

import ru.lebedev.dealership.application.contracts.testdrive.operations.AddCarsForTestDrive;

public interface AddCarsForTestDriveUseCase {
    AddCarsForTestDrive.Response add(AddCarsForTestDrive.Request request);
}
