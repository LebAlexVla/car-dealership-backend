package ru.lebedev.dealership.application.contracts.testdrive.models;

import java.time.LocalDateTime;

public record TestDriveOutputDto(
        String testDriveId,
        String clientId,
        String carVersionId,
        LocalDateTime dateTime
) {
}
