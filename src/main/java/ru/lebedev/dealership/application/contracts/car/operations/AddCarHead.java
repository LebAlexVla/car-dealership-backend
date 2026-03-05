package ru.lebedev.dealership.application.contracts.car.operations;

import ru.lebedev.dealership.application.contracts.car.models.CarHeadInputDto;

public final class AddCarHead {
    private AddCarHead() {
    }

    public record Request(String userId, CarHeadInputDto inputDto) {
    }

    public sealed interface Response permits Success, Failure {
    }

    public record Success(String carHeadId) implements Response {
    }

    public record Failure(String message) implements Response {
    }
}
