package ru.lebedev.dealership.application.contracts.testdrive;

import ru.lebedev.dealership.application.contracts.testdrive.operations.AddTestDrive;

public interface AddTestDriveUseCase {
    AddTestDrive.Response add(AddTestDrive.Request request);
}
