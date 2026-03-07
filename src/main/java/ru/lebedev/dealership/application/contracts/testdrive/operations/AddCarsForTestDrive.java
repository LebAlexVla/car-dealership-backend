package ru.lebedev.dealership.application.contracts.testdrive.operations;

import java.util.List;

public final class AddCarsForTestDrive {
    private AddCarsForTestDrive() {
    }

    public sealed interface Response permits Success, Failure {
    }

    public record Request(String userId, List<String> carVersionsIds) {
    }

    public record Success() implements Response {
    }

    public record Failure(String message) implements Response {
    }
}
