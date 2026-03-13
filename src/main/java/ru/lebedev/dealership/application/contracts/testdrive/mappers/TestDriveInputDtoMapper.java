package ru.lebedev.dealership.application.contracts.testdrive.mappers;

import ru.lebedev.dealership.application.contracts.testdrive.models.TestDriveInputDto;
import ru.lebedev.dealership.domain.testdrive.TestDrive;

public class TestDriveInputDtoMapper {
    public static TestDrive map(TestDriveInputDto dto) {
        return new TestDrive(
                0,
                dto.clientId(),
                dto.carVersionId(),
                dto.dateTime()
        );
    }
}