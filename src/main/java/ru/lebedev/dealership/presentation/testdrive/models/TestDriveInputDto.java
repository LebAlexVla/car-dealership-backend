package ru.lebedev.dealership.presentation.testdrive.models;

import java.time.LocalDateTime;

public record TestDriveInputDto(
        Long clientId,
        Long carVersionId,
        LocalDateTime dateTime
) {
}
