package ru.lebedev.dealership.application.contracts.car.operations;

import ru.lebedev.dealership.application.contracts.car.models.CarHeadOutputDto;

public final class ShowSpecificCarHead {
    private ShowSpecificCarHead() {
    }

    public sealed interface Response permits Success, Failure {
    }

    public record Request(String userId, String carHeadId) {
    }

    public record Success(CarHeadOutputDto carHeadOutputDto) implements Response {
    }

    public record Failure(String message) implements Response {
    }
}