package ru.lebedev.dealership.application.contracts.car.operations;

import ru.lebedev.dealership.application.contracts.car.models.CarVersionOutputDto;

public final class ShowSpecificCarVersion {
    private ShowSpecificCarVersion() {
    }

    public sealed interface Response permits Success, Failure {
    }

    public record Request(String userId, String carVersionId) {
    }

    public record Success(CarVersionOutputDto carVersionOutputDto) implements Response {
    }

    public record Failure(String message) implements Response {
    }
}