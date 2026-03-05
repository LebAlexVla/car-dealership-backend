package ru.lebedev.dealership.application.contracts.car.operations;

import ru.lebedev.dealership.application.contracts.car.models.CarVersionInputDto;

public final class AddCarVersion {
    private AddCarVersion() {
    }

    public sealed interface Response permits Success, Failure {
    }

    public record Request(String userId, CarVersionInputDto inputDto) {
    }

    public record Success(String carVersionId) implements Response {
    }

    public record Failure(String message) implements Response {
    }
}