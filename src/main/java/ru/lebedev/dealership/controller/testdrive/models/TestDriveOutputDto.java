package ru.lebedev.dealership.controller.testdrive.models;

import java.time.LocalDateTime;

public record TestDriveOutputDto(
        Long id,
        Long clientId,
        Long carVersionId,
        LocalDateTime dateTime
) {
}