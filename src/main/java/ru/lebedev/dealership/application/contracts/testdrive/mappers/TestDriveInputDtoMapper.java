package ru.lebedev.dealership.application.contracts.testdrive.mappers;

import ru.lebedev.dealership.application.contracts.testdrive.models.TestDriveInputDto;
import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;
import ru.lebedev.dealership.domain.testdrive.TestDrive;
import ru.lebedev.dealership.domain.testdrive.TestDriveId;
import ru.lebedev.dealership.domain.user.UserId;

import java.util.UUID;

public class TestDriveInputDtoMapper {
    public static TestDrive map(TestDriveId testDriveId, TestDriveInputDto dto) {
        return new TestDrive(
                testDriveId,
                new UserId(UUID.fromString(dto.clientId())),
                new CarVersionId(UUID.fromString(dto.carVersionId())),
                dto.dateTime()
        );
    }
}
