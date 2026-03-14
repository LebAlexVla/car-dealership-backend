package ru.lebedev.dealership.domain.testdrive;

import java.time.LocalDateTime;

public record TestDrive(
        Long testDriveId,
        Long clientId,
        Long carVersionId,
        LocalDateTime dateTime
) {
}