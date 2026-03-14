package ru.lebedev.dealership.application.contracts.testdrive.requests;

import java.util.List;

public record RemoveCarsRequest(Long testDriveId, List<Long> carVersionsIds) {
}