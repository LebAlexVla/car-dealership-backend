package ru.lebedev.dealership.domain.testdrive;

import java.time.LocalDateTime;

public record TestDrive(
        long testDriveId,
        long clientId,
        long carVersionId,
        LocalDateTime dateTime
) {
}