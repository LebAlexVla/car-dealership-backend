package ru.lebedev.dealership.application.contracts.testdrive.operations;

import ru.lebedev.dealership.application.contracts.testdrive.models.TestDriveOutputDto;

import java.util.List;

public final class ShowTestDrives {
    private ShowTestDrives() {
    }

    public sealed interface Response permits Success, Failure {
    }

    public record Request(String userId) {
    }

    public record Success(List<TestDriveOutputDto> outputDtos) implements Response {
    }

    public record Failure(String message) implements Response {
    }
}