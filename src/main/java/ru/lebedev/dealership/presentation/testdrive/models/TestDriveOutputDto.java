package ru.lebedev.dealership.presentation.testdrive.models;

import java.time.LocalDateTime;

public record TestDriveOutputDto(
        Long testDriveId,
        Long clientId,
        Long carVersionId,
        LocalDateTime dateTime
) {
}