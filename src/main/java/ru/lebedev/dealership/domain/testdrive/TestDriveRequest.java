package ru.lebedev.dealership.domain.testdrive;

import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;

import java.time.LocalDateTime;
import java.util.UUID;

public record TestDriveRequest(
        UUID clientId,
        CarVersionId carVersionId,
        LocalDateTime dateTime
) {
}