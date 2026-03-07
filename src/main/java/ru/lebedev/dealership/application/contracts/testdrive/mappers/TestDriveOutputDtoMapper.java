package ru.lebedev.dealership.application.contracts.testdrive.mappers;

import ru.lebedev.dealership.application.contracts.testdrive.models.TestDriveOutputDto;
import ru.lebedev.dealership.domain.testdrive.TestDrive;

public class TestDriveOutputDtoMapper {
    public static TestDriveOutputDto map(TestDrive testDrive) {
        return new TestDriveOutputDto(
                testDrive.testDriveId().value().toString(),
                testDrive.clientId().value().toString(),
                testDrive.carVersionId().value().toString(),
                testDrive.dateTime()
        );
    }
}
