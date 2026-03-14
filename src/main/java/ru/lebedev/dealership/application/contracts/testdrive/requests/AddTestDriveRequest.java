package ru.lebedev.dealership.application.contracts.testdrive.requests;

import ru.lebedev.dealership.application.contracts.testdrive.models.TestDriveInputDto;

public record AddTestDriveRequest(TestDriveInputDto inputDto) {
}