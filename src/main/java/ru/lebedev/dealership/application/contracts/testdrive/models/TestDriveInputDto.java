package ru.lebedev.dealership.application.contracts.testdrive.models;

import java.time.LocalDateTime;

public record TestDriveInputDto(
        long clientId,
        long carVersionId,
        LocalDateTime dateTime
) {
}
