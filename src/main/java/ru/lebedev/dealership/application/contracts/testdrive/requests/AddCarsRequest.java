package ru.lebedev.dealership.application.contracts.testdrive.requests;

import java.util.List;

public record AddCarsRequest(long testDriveId, List<Long> carVersionsIds) {
}