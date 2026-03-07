package ru.lebedev.dealership.application.contracts.testdrive;

import ru.lebedev.dealership.application.contracts.testdrive.operations.ShowTestDrives;

public interface ShowTestDrivesUseCase {
    ShowTestDrives.Response show(ShowTestDrives.Request request);
}