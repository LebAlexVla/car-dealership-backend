package ru.lebedev.dealership.application.contracts.testdrive.operations;

import ru.lebedev.dealership.application.contracts.testdrive.models.TestDriveInputDto;

public final class AddTestDrive {
    private AddTestDrive() {
    }

    public sealed interface Response permits Success, Failure {
    }

    public record Request(String userId, TestDriveInputDto inputDto) {
    }

    public record Success(String testDriveId) implements Response {
    }

    public record Failure(String message) implements Response {
    }
}