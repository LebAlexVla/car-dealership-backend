package ru.lebedev.dealership.controller.testdrive.dto;

import java.time.LocalDateTime;

public record TestDriveOutputDto(
        Long id,
        Long clientId,
        Long carVersionId,
        LocalDateTime dateTime
) {
}