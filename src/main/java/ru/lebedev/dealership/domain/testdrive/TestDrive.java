package ru.lebedev.dealership.domain.testdrive;

import ru.lebedev.dealership.domain.car.vo.CarVersionId;
import ru.lebedev.dealership.domain.user.UserId;

import java.time.LocalDateTime;

public record TestDrive(
        TestDriveId testDriveId,
        UserId clientId,
        CarVersionId carVersionId,
        LocalDateTime dateTime
) {
}