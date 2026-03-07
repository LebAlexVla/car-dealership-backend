package ru.lebedev.dealership.application.contracts.testdrive.models;

import java.time.LocalDateTime;

public record TestDriveInputDto(
        String clientId,
        String carVersionId,
        LocalDateTime dateTime
) {
}
