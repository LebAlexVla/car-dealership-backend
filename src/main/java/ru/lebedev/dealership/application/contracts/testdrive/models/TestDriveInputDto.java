package ru.lebedev.dealership.application.contracts.testdrive.models;

import java.time.LocalDateTime;

public record TestDriveInputDto(
        Long clientId,
        Long carVersionId,
        LocalDateTime dateTime
) {
}
